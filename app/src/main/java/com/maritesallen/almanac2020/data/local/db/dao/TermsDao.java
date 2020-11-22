package com.maritesallen.almanac2020.data.local.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.maritesallen.almanac2020.data.model.db.terms.Terms;

import java.util.List;

/**
 * Author : Arvindo Mondal
 * Created on 09-12-2019
 */
@Dao
public interface TermsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Terms model);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Terms> modelList);

    @Query("SELECT * FROM termsPrivacy")
    List<Terms> getTermsList();
}
