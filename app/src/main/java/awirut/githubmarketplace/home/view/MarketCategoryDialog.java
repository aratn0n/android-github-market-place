package awirut.githubmarketplace.home.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import awirut.githubmarketplace.R;

public class MarketCategoryDialog extends DialogFragment {

    public interface MarketCategoryDialogListener
    {
        void onClickCategory(int index);
    }

    public static String TAG = "MARKET_CATEGORY_DIALOG";
    private static String CATEGORY_LIST_KEY = "CATEGORY_LIST";

    private ArrayList<String> categoryList = new ArrayList<>();
    private MarketCategoryDialogListener listener;

    public static MarketCategoryDialog create(ArrayList<String> categoryList)
    {
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(CATEGORY_LIST_KEY, categoryList);

        MarketCategoryDialog marketCategoryDialog = new MarketCategoryDialog();
        marketCategoryDialog.setArguments(bundle);

        return marketCategoryDialog;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if(bundle != null && bundle.containsKey(CATEGORY_LIST_KEY))
            categoryList = bundle.getStringArrayList(CATEGORY_LIST_KEY);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (MarketCategoryDialogListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new MaterialDialog.Builder(getContext())
                .title(getString(R.string.home_screen_select_category))
                .items(categoryList)
                .itemsCallback((dialog, itemView, position, text) -> {
                    if(listener != null)
                        listener.onClickCategory(position);
                })
                .build();
    }
}
