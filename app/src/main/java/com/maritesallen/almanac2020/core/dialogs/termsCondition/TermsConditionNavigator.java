package com.maritesallen.almanac2020.core.dialogs.termsCondition;

import com.maritesallen.almanac2020.data.model.db.terms.Terms;

import java.util.List;

interface TermsConditionNavigator {
    void onCancelClick();

    void onAcceptClick();

    void onDeclineClick();

    void onTermsUpdate(List<Terms> terms);
}
