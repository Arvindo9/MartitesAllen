package com.maritesallen.almanac2020.core.dialogs.termsCondition;

import com.maritesallen.almanac2020.base.BaseViewModel;
import com.maritesallen.almanac2020.data.DataManager;
import com.maritesallen.almanac2020.data.model.db.terms.Terms;
import com.maritesallen.almanac2020.utils.rx.SchedulerProvider;

import java.util.List;

public class TermsConditionViewModel extends BaseViewModel<TermsConditionNavigator> {

    public TermsConditionViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
//        getTerms();
        getTermsDb();
    }

    //resource------------------

    public void onCancelClick(){
        getNavigator().onCancelClick();
    }
    public void onAcceptClick(){
        getNavigator().onAcceptClick();
    }
    public void onDeclineClick(){
        getNavigator().onDeclineClick();
    }


    private void getTermsDb(){
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getTerms()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null && !response.isEmpty()){
                        getNavigator().onTermsUpdate(response);
                    }
                    else {
                        getTerms();
                    }
                    setIsLoading(false);
                }, throwable ->{
                    getTerms();
                    throwable.printStackTrace();
                    setIsLoading(false);
                }));
    }

    private void getTerms(){
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .termsAndPrivacy()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null){
                        if(response.getSuccess() && response.getData() != null) {
                            getNavigator().onTermsUpdate(response.getData().getTerms());
                            saveTerms(response.getData().getTerms());
                        }
                        else {
                            getNavigator().onTermsUpdate(null);
                        }
                    }
                    else {
                        getNavigator().onTermsUpdate(null);
                    }
                    setIsLoading(false);
                }, throwable ->{
                    getNavigator().onTermsUpdate(null);
                    throwable.printStackTrace();
                    setIsLoading(false);
                }));
    }


    private void saveTerms(List<Terms> terms) {
        getCompositeDisposable().add(getDataManager()
                .saveTerms(terms)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().newThread())
                .subscribe(response -> {
                    if (response != null && response){
                        //Saved succeful
                    }
                }, Throwable::printStackTrace));
    }

    public void cancel() {
        setIsLoading(false);
    }
}
