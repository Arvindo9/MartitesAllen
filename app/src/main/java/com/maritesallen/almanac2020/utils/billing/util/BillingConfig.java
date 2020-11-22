package com.maritesallen.almanac2020.utils.billing.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;

import com.maritesallen.almanac2020.utils.billing.core.IabHelper;
import com.maritesallen.almanac2020.utils.billing.core.IabResult;
import com.maritesallen.almanac2020.utils.billing.core.Inventory;
import com.maritesallen.almanac2020.utils.billing.core.Purchase;


/**
 * Author : Arvindo Mondal
 * Created on 04-12-2019
 */
public class BillingConfig {
    static final String TAG = BillingConfig.class.getSimpleName();
    private BillingConfigListener callback;

    public interface BillingConfigListener{
        void loading(boolean status);

        void onConsumeFinished(Purchase purchase, IabResult result);
    }

    private static String PURCHASE_ITEM_ID = "purchase_book";

    // (arbitrary) request code for the purchase flow
    private static final int RC_REQUEST = 10001;

    // The helper object
    private IabHelper mHelper;

//    private String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAk3/SlCJeUB8BfILLYAlaA3B4gg9ZR2qffMHFVvEC9+JMELa00WKmYu+pMg4inPy7nmQZLBUe9pnot7cfk8911Rd3ZgLjmcCCgbhV/2r+l1uZwLYIdhaKGaw7ArsP2Cegk4cXZx55xpArbXiZ1mnPfz2NLLYLR8NisDRXNWfZ770UVt4hic4mrPFIm3E4G9uuXHG3K4brctX4HBca/vnGSPXnMOeOSD6vXd6fV7XVDyKsKJ5vuFqg0Ni1ZqRsy94k/Kt5CkAt5Oj7blSAtwbqzau02SCEtqPjffA1BXrRHQzKnySkFHjSXwASEAzISVMbUJyc5y8Jh/ybrEnjk0AuDwIDAQAB";
    private String base64EncodedPublicKey;// = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAk3/SlCJeUB8BfILLYAlaA3B4gg9ZR2qffMHFVvEC9+JMELa00WKmYu+pMg4inPy7nmQZLBUe9pnot7cfk8911Rd3ZgLjmcCCgbhV/2r+l1uZwLYIdhaKGaw7ArsP2Cegk4cXZx55xpArbXiZ1mnPfz2NLLYLR8NisDRXNWfZ770UVt4hic4mrPFIm3E4G9uuXHG3K4brctX4HBca/vnGSPXnMOeOSD6vXd6fV7XVDyKsKJ5vuFqg0Ni1ZqRsy94k/Kt5CkAt5Oj7blSAtwbqzau02SCEtqPjffA1BXrRHQzKnySkFHjSXwASEAzISVMbUJyc5y8Jh/ybrEnjk0AuDwIDAQAB";

    private Context context;

    private BillingConfig(){
    }

    public static BillingConfig getInstance(){
        return new BillingConfig();
    }

    public void initialise(Context context, String base64EncodedPublicKey){
        this.context = context;
        this.base64EncodedPublicKey =base64EncodedPublicKey;
    }

    public void setCallback(BillingConfigListener callback){
        this.callback = callback;
    }

    public IabHelper getmHelper(){
        return mHelper;
    }

    public void setUp(String itemId) {
        // load game data

        /* base64EncodedPublicKey should be YOUR APPLICATION'S PUBLIC KEY
         * (that you got from the Google Play developer console). This is not your
         * developer public key, it's the *app-specific* public key.
         *
         * Instead of just storing the entire literal string here embedded in the
         * program,  construct the key at runtime from pieces or
         * use bit manipulation (for example, XOR with some other string) to hide
         * the actual key.  The key itself is not secret information, but we don't
         * want to make it easy for an attacker to replace the public key with one
         * of their own and then fake messages from the server.
         */
        PURCHASE_ITEM_ID = itemId;

        // Create the helper, passing it our context and the public key to verify signatures with
        Log.d(TAG, "Creating IAB helper.");
        mHelper = new IabHelper(context, base64EncodedPublicKey);

        // enable debug logging (for a production application, you should set this to false).
        mHelper.enableDebugLogging(true);

        // Start setup. This is asynchronous and the specified listener
        // will be called once setup completes.
        Log.d(TAG, "Starting setup.");
        mHelper.startSetup(result -> {
            Log.d(TAG, "Setup finished.");

            if (!result.isSuccess()) {
                // Oh noes, there was a problem.
                complain("Problem setting up in-app billing: " + result);
                return;
            }

            // Have we been disposed of in the meantime? If so, quit.
            if (mHelper == null) return;

            // IAB is fully set up. Now, let's get an inventory of stuff we own.
            Log.d(TAG, "Setup successful. Querying inventory.");
            try {
                mHelper.queryInventoryAsync(mGotInventoryListener);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    // User clicked the "Buy Gas" button
    public void doPurchase(String payload) {
        Log.d(TAG, "Buy gas button clicked.");

        // launch the gas purchase UI flow.
        // We will be notified of completion via mPurchaseFinishedListener
        callback.loading(true);
        Log.d(TAG, "Launching purchase flow for gas.");

        /* TODO: for security, generate your payload here for verification. See the comments on
         *        verifyDeveloperPayload() for more info. Since this is a SAMPLE, we just use
         *        an empty string, but on a production app you should carefully generate this. */

        mHelper.launchPurchaseFlow((Activity) context, PURCHASE_ITEM_ID, RC_REQUEST,
                mPurchaseFinishedListener, payload);
    }


    // Callback for when a purchase is finished
    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            Log.d(TAG, "Purchase finished: " + result + ", purchase: " + purchase);

            // if we were disposed of in the meantime, quit.
            if (mHelper == null) return;

            if (result.isFailure()) {
//                complain("Error purchasing: " + result);
                callback.loading(false);
                return;
            }
            if (!verifyDeveloperPayload(purchase)) {
                complain("Error purchasing. Authenticity verification failed.");
                callback.loading(false);
                return;
            }

            Log.d(TAG, "Purchase successful.");

            if (purchase.getSku().equals(PURCHASE_ITEM_ID)) {
                // bought 1/4 tank of gas. So consume it.
                Log.d(TAG, "Purchase is gas. Starting gas consumption.");
                mHelper.consumeAsync(purchase, mConsumeFinishedListener);
            }
        }
    };


    // Listener that's called when we finish querying the items and subscriptions we own
    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new
            IabHelper.QueryInventoryFinishedListener() {
                public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
                    Log.d(TAG, "Query inventory finished.");

                    // Have we been disposed of in the meantime? If so, quit.
                    if (mHelper == null) return;

                    // Is it a failure?
                    if (result.isFailure()) {
                        complain("Failed to query inventory: " + result);
                        return;
                    }

                    Log.d(TAG, "Query inventory was successful.");

                    /*
                     * Check for items we own. Notice that for each purchase, we check
                     * the developer payload to see if it's correct! See
                     * verifyDeveloperPayload().
                     */

                    // Check for gas delivery -- if we own gas, we should fill up the tank immediately
                    Purchase gasPurchase = inventory.getPurchase(PURCHASE_ITEM_ID);
                    if (gasPurchase != null && verifyDeveloperPayload(gasPurchase)) {
                        Log.d(TAG, "We have gas. Consuming it.");
                        mHelper.consumeAsync(inventory.getPurchase(PURCHASE_ITEM_ID), mConsumeFinishedListener);
                        return;
                    }

                    callback.loading(false);
                    Log.d(TAG, "Initial inventory query finished; enabling main UI.");
                }
            };

    // Called when consumption is complete
    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
        public void onConsumeFinished(Purchase purchase, IabResult result) {
            Log.d(TAG, "Consumption finished. Purchase: " + purchase + ", result: " + result);

            // if we were disposed of in the meantime, quit.
            if (mHelper == null) return;

            // We know this is the "gas" sku because it's the only one we consume,
            // so we don't check which sku was consumed. If you have more than one
            // sku, you probably should check...
            callback.onConsumeFinished(purchase, result);
            callback.loading(false);
            Log.d(TAG, "End consumption flow.");
        }
    };

    /** Verifies the developer payload of a purchase. */
    private boolean verifyDeveloperPayload(Purchase p) {

        /*
         * TODO: verify that the developer payload of the purchase is correct. It will be
         * the same one that you sent when initiating the purchase.
         *
         * WARNING: Locally generating a random string when starting a purchase and
         * verifying it here might seem like a good approach, but this will fail in the
         * case where the user purchases an item on one device and then uses your app on
         * a different device, because on the other device you will not have access to the
         * random string you originally generated.
         *
         * So a good developer payload has these characteristics:
         *
         * 1. If two different users purchase an item, the payload is different between them,
         *    so that one user's purchase can't be replayed to another user.
         *
         * 2. The payload must be such that you can verify it even when the app wasn't the
         *    one who initiated the purchase flow (so that items purchased by the user on
         *    one device work on other devices owned by the user).
         *
         * Using your own server to store and verify developer payloads across app
         * installations is recommended.
         */

        return true;
    }

    private void complain(String message) {
        Log.e(TAG, "**** TrivialDrive Error: " + message);
        alert("Error: " + message);
    }

    private void alert(String message) {
        AlertDialog.Builder bld = new AlertDialog.Builder(context);
        bld.setMessage(message);
        bld.setNeutralButton("OK", null);
        Log.d(TAG, "Showing alert dialog: " + message);
        bld.create().show();
    }
}
