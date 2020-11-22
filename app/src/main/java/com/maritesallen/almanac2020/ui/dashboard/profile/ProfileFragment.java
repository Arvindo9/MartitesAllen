package com.maritesallen.almanac2020.ui.dashboard.profile;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.maritesallen.almanac2020.R;
import com.maritesallen.almanac2020.base.BaseFragment;
import com.maritesallen.almanac2020.core.binding.BindingUtils;
import com.maritesallen.almanac2020.core.dialogs.DialogListener;
import com.maritesallen.almanac2020.core.dialogs.date.DateDialog;
import com.maritesallen.almanac2020.core.dialogs.termsCondition.TermsConditionDialog;
import com.maritesallen.almanac2020.core.dialogs.upgrade.UpgradeDialog;
import com.maritesallen.almanac2020.data.model.db.books.BookPremium;
import com.maritesallen.almanac2020.databinding.FragmentProfileBinding;
import com.maritesallen.almanac2020.di.builder.ViewModelProviderFactory;
import com.maritesallen.almanac2020.ui.dashboard.DashboardActivity;
import com.maritesallen.almanac2020.utils.AppConstants;
import com.maritesallen.almanac2020.utils.Logger;
import com.maritesallen.almanac2020.utils.util.FileProvider;
import com.maritesallen.almanac2020.utils.util.General;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import static android.app.Activity.RESULT_OK;
import static com.facebook.FacebookSdk.getCacheDir;
import static com.maritesallen.almanac2020.core.dialogs.termsCondition.TermsConditionDialog.TYPE_ABOUT;
import static com.maritesallen.almanac2020.core.dialogs.termsCondition.TermsConditionDialog.TYPE_APP_INFO;
import static com.maritesallen.almanac2020.core.dialogs.termsCondition.TermsConditionDialog.TYPE_PRIVACY;
import static com.maritesallen.almanac2020.core.dialogs.termsCondition.TermsConditionDialog.TYPE_TERMS_INSIDE;
import static com.maritesallen.almanac2020.utils.AppConstants.DIALOG_SIZE_FULL;

/**
 * Author       : Arvindo Mondal
 * Created on   : 13-11-2019
 * Designation  : Programmer
 * Strength     : Never give up
 * Motto        : To be known as great Mathematician
 * Skills       : Algorithms and logic
 */
public class ProfileFragment extends BaseFragment<FragmentProfileBinding, ProfileViewModel>
        implements ProfileNavigator, DialogListener, DateDialog.DateInterface {
    public static final String TAG = ProfileFragment.class.getSimpleName();
    public static final int PICK_IMAGE = 21;
    public static final int PICK_IMAGES = 20;

    @Inject
    ViewModelProviderFactory factory;
    private FragmentProfileBinding binding;
    private ProfileViewModel viewModel;
    private boolean isProfileCanEdit = false;

    private File file = null;
    private boolean lockAspectRatio = false;

    private int ASPECT_RATIO_X = 16, ASPECT_RATIO_Y = 9, bitmapMaxWidth = 1000, bitmapMaxHeight = 1000;
    private int IMAGE_COMPRESSION = 100;
    public static String fileName;
    private boolean setBitmapMaxWidthHeight = false;
    private String birthDate = "";

    /**
     * @param binding is used in current attached fragment
     */
    @Override
    public void getFragmentBinding(FragmentProfileBinding binding) {
        this.binding = binding;
    }

    /**
     * @return R.layout.layout_file
     */
    @Override
    public int getLayout() {
        return R.layout.fragment_profile;
    }

    /**
     * Override for set view model
     *
     * @return viewModel = ViewModelProviders.of(this,factory).get(WelcomeViewModel.class);
     */
    @Override
    public ProfileViewModel getViewModel() {
        return viewModel = ViewModelProviders.of(this,factory).get(ProfileViewModel.class);
    }

    /**
     * Override for set binding variable
     *
     * @return BR.CalendarData;
     */
    @Override
    public int getBindingVariable() {
        return com.maritesallen.almanac2020.BR.data;
    }

    /**
     * Write rest of the code of fragment in onCreateView after view created
     */
    @Override
    protected void init() {
        viewModel.setNavigator(this);
        setUp();
        viewModel.loadProfileDataDb();
        setUpAds();
    }

    private void setUp() {
        ((DashboardActivity)getBaseActivity()).setProfile();
        if(viewModel.isPremium()){
            binding.profileUpgradeCard.layout.setVisibility(View.GONE);
        }
    }

    private void setUpAds() {
//        if(viewModel.isPremium()) {
//            binding.layoutAds.layoutAds.setVisibility(View.GONE);
//        }
//        else{
            AdView adView = new AdView(getBaseActivity());
            adView.setAdSize(AdSize.LARGE_BANNER);
            adView.setAdUnitId(viewModel.getAdsUnitId());
            Logger.e("getAdsUnitId: "+viewModel.getAdsUnitId());

            MobileAds.initialize(getBaseActivity(), initializationStatus -> {

            });
            AdRequest adRequest = new AdRequest.Builder().build();
            adView.loadAd(adRequest);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            binding.layoutAds.layoutAds.addView(adView, params);

//            binding.layoutAds.adView.loadAd(adRequest);
//        }
    }

    @Override
    public void onUpgradeClick(View view) {
        getBaseActivity().startDialog(new UpgradeDialog(DIALOG_SIZE_FULL, this), UpgradeDialog.TAG);
    }

    @Override
    public void onRestoreClick(View view) {
//        viewModel.restoreApp();
        if(getBaseActivity() != null){
            ((DashboardActivity)getBaseActivity()).restore();
        }
    }

    @Override
    public void onTermsClick(View view) {
        if(isNetworkConnected()) {
            getBaseActivity().startDialog(new TermsConditionDialog(DIALOG_SIZE_FULL, this, TYPE_TERMS_INSIDE),
                    TermsConditionDialog.TAG);
        }
        else{
            showMessage(R.string.network_error);
        }
    }

    @Override
    public void onPrivacyClick(View view) {
        if(isNetworkConnected()) {
            getBaseActivity().startDialog(new TermsConditionDialog(DIALOG_SIZE_FULL, this, TYPE_PRIVACY), TermsConditionDialog.TAG);
        }
        else{
            showMessage(R.string.network_error);
        }
    }

    @Override
    public void onChangeProfileImageClick() {
        if(isProfileCanEdit){
            ((DashboardActivity)getBaseActivity()).selectPhoto();
//            Intent intent = new Intent();
//            intent.setType("image/*");
//            intent.setAction(Intent.ACTION_GET_CONTENT);
//            startActivityForResult(Intent.createChooser(intent, getString(R.string.select_pic)), PICK_IMAGE);

/*
            Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
            getIntent.setType("image/*");

            Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickIntent.setType("image/*");

            Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});
            startActivityForResult(chooserIntent, PICK_IMAGE);
*/
        }
    }

    @Override
    public void onEditProfileClick() {
        isProfileCanEdit = true;
        onEditProfile();
    }

    @Override
    public void onAppInfoClick(View view) {
        if(isNetworkConnected()) {
            getBaseActivity().startDialog(new TermsConditionDialog(DIALOG_SIZE_FULL, this, TYPE_APP_INFO),
                    TermsConditionDialog.TAG);
        }
        else{
            showMessage(R.string.network_error);
        }
    }

    @Override
    public void onAboutClick(View view) {
        if(isNetworkConnected()) {
            getBaseActivity().startDialog(new TermsConditionDialog(DIALOG_SIZE_FULL, this, TYPE_ABOUT),
                    TermsConditionDialog.TAG);
        }
        else{
            showMessage(R.string.network_error);
        }
    }

    @Override
    public void editCompleted() {
        file = null;
        if(viewModel.getProfilePic().isEmpty()) {
            binding.profileInfo.profileImage.setImageDrawable(VectorDrawableCompat.create(getResources(),
                    R.drawable.ic_user_profile_default, null));
        }
        else{
//            viewModel.setProfilePicReset();
            BindingUtils.setImageUrl(binding.profileInfo.profileImage, viewModel.getProfilePic());
        }
    }

    @Override
    public void onDateClick(View view) {
        if(viewModel.isDobUpdate()) {
            getBaseActivity().hideKeyboard();
            birthDate = "";

            new AlertDialog.Builder(getBaseActivity())
                    .setTitle(getString(R.string.dob))
                    .setMessage(R.string.dob_edit_note)
                    .setCancelable(false)
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                        DateDialog dateFragment = DateDialog.newInstance(this);
                        dateFragment.show(getBaseActivity().getSupportFragmentManager(), DateDialog.TAG);
                    })
                    .setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.dismiss())
                    .create()
                    .show();

        }
        else{
            new AlertDialog.Builder(getBaseActivity())
                    .setTitle(getString(R.string.dob))
                    .setMessage(R.string.dob_edit_error)
                    .setCancelable(false)
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> dialog.dismiss())
                    .create()
                    .show();
        }
    }

    @Override
    public void showMessage(int message) {
        if(getBaseActivity() != null) {
            getBaseActivity().showToast(message);
        }
    }

    @Override
    public void downloadBooks(List<BookPremium> bookPremium) {
        if(getBaseActivity() != null){
//            ((DashboardActivity)getBaseActivity()).downloadBooks();
        }
    }

    @Override
    public void offlineMode(boolean status) {
        if(status){
            binding.profileSheepCard.layout.setVisibility(View.GONE);
            binding.offlineCard.layout.setVisibility(View.VISIBLE);
        }else {
            binding.profileSheepCard.layout.setVisibility(View.VISIBLE);
            binding.offlineCard.layout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onEditProfileCancelClick() {
        reset();
        if(viewModel.getProfilePic().isEmpty()) {
            binding.profileInfo.profileImage.setImageDrawable(VectorDrawableCompat.create(getResources(),
                    R.drawable.ic_user_profile_default, null));
        }
        else{
//            viewModel.setProfilePicReset();
            BindingUtils.setImageUrl(binding.profileInfo.profileImage, viewModel.getProfilePic());
        }
        file = null;
    }

    private void reset(){
        binding.profileInfo.changeProfilePicLayout.setVisibility(View.GONE);
        binding.profileInfo.nameE.setVisibility(View.GONE);
        binding.profileInfo.nameT.setVisibility(View.VISIBLE);
        binding.profileInfo.birthDateE.setVisibility(View.GONE);
        binding.profileInfo.birthDateT.setVisibility(View.VISIBLE);
        binding.profileInfo.profileSaveLayout.setVisibility(View.GONE);
        binding.profileInfo.editProfileLayout.setVisibility(View.VISIBLE);
        isProfileCanEdit = false;
    }

    @Override
    public void onEditProfileSaveClick() {
        onSaveProfile();
    }

    //Task----------------------

    private void onEditProfile(){
        birthDate = "";
        binding.profileInfo.changeProfilePicLayout.setVisibility(View.VISIBLE);
        binding.profileInfo.nameE.setVisibility(View.VISIBLE);
        binding.profileInfo.nameT.setVisibility(View.GONE);
        binding.profileInfo.birthDateE.setVisibility(View.VISIBLE);
        binding.profileInfo.birthDateT.setVisibility(View.GONE);
        binding.profileInfo.profileSaveLayout.setVisibility(View.VISIBLE);
        binding.profileInfo.editProfileLayout.setVisibility(View.GONE);

        binding.profileInfo.nameE.setText(binding.profileInfo.nameT.getText().toString());
        binding.profileInfo.birthDateE.setText(binding.profileInfo.birthDateT.getText().toString());
    }

    private void onSaveProfile(){
        reset();

        String name = binding.profileInfo.nameE.getText() != null?binding.profileInfo.nameE.getText().toString() : "";
        String birthDate = binding.profileInfo.birthDateE.getText() != null?binding.profileInfo.birthDateE.getText().toString() : "";

        binding.profileInfo.nameT.setText(name);
        binding.profileInfo.birthDateT.setText(birthDate);

        if(this.birthDate.isEmpty()){
            birthDate = "";
        }
        else{
            birthDate = this.birthDate;
        }
        viewModel.updateProfile(file, name, birthDate);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
//            final Uri resultUri = UCrop.getOutput(data);
            handleUCropResult(data);
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }
        else if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            if (data == null || data.getData() == null) {
                //Display an error
                return;
            }

            Uri imageUri = data.getData();
            cropImage(imageUri);
/*
            //Method 2 working
            final Uri uri = data.getCalendarData();
            String filePath;
            try {
                filePath = FileProvider.getFilePath(getBaseActivity(), uri);
                if (filePath != null) {
                    try {
                        binding.profileInfo.profileImage.setImageBitmap(BitmapFactory.decodeFile(filePath));

                        file = new File(filePath);
                        if (file.isFile()) {
                            Logger.e("file exist");
                            Logger.e("file name" + file.getName());
                        } else {
                            Logger.e("file not exist");
                            if(getBaseActivity() != null){
                                getBaseActivity().showToast(R.string.file_not_found);
                            }
                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();

                        if(getBaseActivity() != null){
                            getBaseActivity().showToast(R.string.file_not_found);
                        }
                    }
                }
            }catch (Exception ignore){
                if(getBaseActivity() != null){
                    getBaseActivity().showToast(R.string.file_not_found);
                }
            }
            */
        }
    }

    private void onImageGet(Uri uri){
        String filePath;
        try {
            filePath = FileProvider.getFilePath(getBaseActivity(), uri);
            if (filePath != null) {
                try {
                    binding.profileInfo.profileImage.setImageBitmap(BitmapFactory.decodeFile(filePath));

                    file = new File(filePath);
                    if (file.isFile()) {
                        Logger.e("file exist");
                        Logger.e("file name" + file.getName());

                        try {
                            File f = saveBitmapToFile(file);
                            if(f != null){
                                file = saveBitmapToFile(file);
                            }
                        }catch (Exception ignored){

                        }
                    } else {
                        Logger.e("file not exist");
                        if(getBaseActivity() != null){
                            getBaseActivity().showToast(R.string.file_not_found);
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();

                    if(getBaseActivity() != null){
                        getBaseActivity().showToast(R.string.file_not_found);
                    }
                }
            }
        }catch (Exception ignore){
            if(getBaseActivity() != null){
                getBaseActivity().showToast(R.string.file_not_found);
            }
        }
    }

    //Validation

    private boolean validateFirstName(EditText editText) {
        if (editText.getText().toString().trim().isEmpty()) {
            editText.setError(getString(R.string.invalid_first_name));
            getBaseActivity().requestFocus(editText);
            return false;
        } else {
            editText.setError(null);
        }

        return true;
    }

    /**
     * Default response method of a dialog
     *
     * @param tag    class name from which the response is getting
     * @param params string array with relative CalendarData
     */
    @Override
    public void onSuccessDialogResponse(String tag, String... params) {
        if(tag.equals(UpgradeDialog.TAG)){
            if(params != null && params.length > 0){
                if(params[0].equals(AppConstants.YES)){
                    ((DashboardActivity)getBaseActivity()).doPremiumPurchase();
                }
            }
        }
    }

    //Crop Image-----------------------
    private void cropImage(Uri sourceUri) {
        Uri destinationUri = Uri.fromFile(new File(getCacheDir(), queryName(getBaseActivity().getContentResolver(), sourceUri)));
        UCrop.Options options = new UCrop.Options();
        options.setCompressionQuality(IMAGE_COMPRESSION);

        // applying UI theme
        options.setToolbarColor(ContextCompat.getColor(getBaseActivity(), R.color.colorPrimary));
        options.setStatusBarColor(ContextCompat.getColor(getBaseActivity(), R.color.colorPrimary));
        options.setActiveWidgetColor(ContextCompat.getColor(getBaseActivity(), R.color.colorPrimary));

        if (lockAspectRatio)
            options.withAspectRatio(ASPECT_RATIO_X, ASPECT_RATIO_Y);

        if (setBitmapMaxWidthHeight)
            options.withMaxResultSize(bitmapMaxWidth, bitmapMaxHeight);

        ((DashboardActivity)getBaseActivity()).cropImage(sourceUri, destinationUri, options);

//        UCrop.of(sourceUri, destinationUri)
//                .withOptions(options)
//                .start(getBaseActivity(),  this);
    }

    private static String queryName(ContentResolver resolver, Uri uri) {
        Cursor returnCursor =
                resolver.query(uri, null, null, null, null);
        assert returnCursor != null;
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        returnCursor.moveToFirst();
        String name = returnCursor.getString(nameIndex);
        returnCursor.close();
        return name;
    }

    private void handleUCropResult(Intent data) {
        if (data == null) {
            //Fail crop
            return;
        }
        final Uri resultUri = UCrop.getOutput(data);
        onImageGet(resultUri);
    }

    private Bitmap getImageAttribute(Bitmap bitmap, String path) {
        //Bitmap bmp = null;
        try {
            ExifInterface ei = new ExifInterface(path);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    bitmap = rotateImage(bitmap, 90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    bitmap = rotateImage(bitmap, 180);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    bitmap = rotateImage(bitmap, 270);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Bitmap retVal;

        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        retVal = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
        return retVal;
    }

    /**
     * Calling this will delete the images from cache directory
     * useful to clear some memory
     */
    public static void clearCache(Context context) {
        File path = new File(context.getExternalCacheDir(), "camera");
        if (path.exists() && path.isDirectory()) {
            for (File child : path.listFiles()) {
                child.delete();
            }
        }
    }

    /**
     * reduces the size of the image
     * @param image
     * @param maxSize
     * @return
     */
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public File saveBitmapToFile(File file){
        try {

            // BitmapFactory options to downsize the image
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 6;
            // factor of downsizing the image

            FileInputStream inputStream = new FileInputStream(file);
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();

            // The new size we want to scale to
            final int REQUIRED_SIZE=75;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while(o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(file);

            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            inputStream.close();

            // here i override the original image file
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);

            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100 , outputStream);

            return file;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param date   date will return in string format
     * @param params date will return in int format
     */
    @Override
    public void date(String date, String... params) {
        getBaseActivity().hideKeyboard();

        if(!isValidLastDate(params)){
            getBaseActivity().showToast(R.string.last_date_error);
        }
        else if(isValidDate(params)){
            binding.profileInfo.birthDateE.setText(General.getDateReadable(date));
            birthDate = params[0];
        }
        else{
            getBaseActivity().showToast(R.string.future_date_error);
        }
    }

    private boolean isValidDate(String[] params) {
        int currentDate = General.getDateInt(new Date());
        int enterDate = Integer.parseInt(params[1]);
        if(enterDate <= currentDate){
            return true;
        }

        return false;
    }

    private boolean isValidLastDate(String[] params) {
        int enterDate = Integer.parseInt(params[4]);
        if(enterDate >= 1932){
            return true;
        }

        return false;
    }

    //-------------------------


}
