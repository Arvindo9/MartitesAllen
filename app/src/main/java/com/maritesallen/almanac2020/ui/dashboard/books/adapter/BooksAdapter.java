package com.maritesallen.almanac2020.ui.dashboard.books.adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;

import com.maritesallen.almanac2020.R;
import com.maritesallen.almanac2020.base.BaseAdapter;
import com.maritesallen.almanac2020.data.model.apis.books.Books;
import com.maritesallen.almanac2020.data.model.db.books.BookPremium;
import com.maritesallen.almanac2020.databinding.AdapterBooksBinding;

import java.util.ArrayList;
import java.util.List;

public class BooksAdapter extends BaseAdapter<AdapterBooksBinding, Books> {

    private final Context context;
    private Listener listener;
    private List<BookPremium> premiumBookModel;

    public void setPremiumBook(List<BookPremium> premiumBookModel) {
        this.premiumBookModel.addAll(premiumBookModel);
    }

    public interface Listener {
        void onRetryClick();

        void onBookClick(Books data, int position);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    /**
     * @param adapterList list args require to bind adapter up to the size of array
     */
    public BooksAdapter(Context context, ArrayList<Books> adapterList) {
        super(adapterList);
        premiumBookModel = new ArrayList<>();
        this.context = context;
    }

    /**
     * @param position current index of ArrayList
     * @return return 0 if single layout xml else override this method for multiple xml or elements
     */
    @Override
    protected int getItemViewTypeAdapter(int position) {
        return 0;
    }

    /**
     * @return R.layout.layout_file
     */
    @Override
    protected int getLayout() {
        return R.layout.adapter_books;
    }

    @Override
    protected void doJobInOnCreate(ViewGroup viewGroup, int viewType, AdapterBooksBinding binding) {
        // work here if you need to control height of your items
        // keep in mind that parent is RecyclerView in this case
//        int height = viewGroup.getMeasuredHeight() / 10;
//        binding.getRoot().setMinimumHeight(height);

        GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) binding.getRoot().getLayoutParams();
        if(isTablet(context)) {
            lp.height = viewGroup.getMeasuredHeight() / 4;
        }
        else{
            lp.height = viewGroup.getMeasuredHeight() / 5;
        }
//        lp.width = viewGroup.getMeasuredWidth();
        binding.getRoot().setLayoutParams(lp);
//        return binding;
    }

    public boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean large = ((context.getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }

    /**
     * Initialised View Holder
     *
     * @param binding DataBinding
     * @return new ViewHolder<B, D>(binding);
     */
    @Override
    public ViewHolder getViewHolder(AdapterBooksBinding binding) {
        return new ViewHolder<AdapterBooksBinding, Books>(binding){
            @Override
            protected void doSomeWorkHere(AdapterBooksBinding binding, Books data, int position) {
                boolean isPurchase = false;

                for(int i=0; i < premiumBookModel.size() && !isPurchase; i++){
                    if(data.getId().equals(premiumBookModel.get(i).getBookId())){
                        isPurchase = true;
                    }
                }
                if(isPurchase){
                    binding.price.setText(R.string.read);
                }
                else{
                    Spanned currencyCode;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        currencyCode = Html.fromHtml(data.getCurrencyCode(), Html.FROM_HTML_MODE_COMPACT);
                    } else {
                        currencyCode = Html.fromHtml(data.getCurrencyCode());
                    }

                    String price = currencyCode + " " + data.getPrice();
                    binding.price.setText(price);
                }
            }

            /**
             * @param data binding.setCalendarData(new AdapterViewModel(Data));
             */
            @Override
            protected void bindData(Books data) {
                binding.setData(new BooksAdapterViewModel(data));
            }

            /**
             * Method to set click listeners on view holder or groups
             *
             * @param thisContext set it on method : binding.layout.setOnClickListener(thisContext);
             * @param binding     DataBinding
             * @param data        Data
             */
            @Override
            public void setClickListeners(ViewHolderClickListener thisContext, AdapterBooksBinding binding, Books data) {
                binding.bookImage.setOnClickListener(thisContext);
            }

            /**
             * Initialised holder by new operator
             *
             * @param binding  DataBinding
             * @param data     dataList
             * @param position of adapter
             * @return new ViewHolderClickListener<B, D>(binding, Data, position)
             */
            @Override
            protected ViewHolderClickListener viewHolderReference(AdapterBooksBinding binding, Books data, int position) {
                return new ViewHolderClickListener<AdapterBooksBinding, Books>(binding, data, position) {
                    /**
                     * Called when a view has been clicked.
                     *
                     * @param view The view that was clicked.
                     *             switch (view.getId()){
                     *             case R.id.id:
                     *             // itemView.getContext().startActivity();
                     *             break;
                     *             }
                     */
                    @Override
                    public void onClick(View view) {
                        if(view.getId() == R.id.bookImage){
                            listener.onBookClick(data, position);
//                            if(itemView.getContext() instanceof DashboardActivity) {
//                                ((DashboardActivity)itemView.getContext()).startDialog(BookPurchaseDialog);
//                            }
                        }
                    }
                };
            }
        };
    }

    /**
     * @return new FilterClass();
     */
    @Override
    protected FilterClass initialisedFilterClass() {
        return null;
    }
}
