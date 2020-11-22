package com.maritesallen.almanac2020.data.local.db.dao.books;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.maritesallen.almanac2020.data.model.db.books.BookPremium;

import java.util.List;

/**
 * Author : Arvindo Mondal
 * Created on 19-12-2019
 */
@Dao
public interface BookPremiumDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(BookPremium model);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<BookPremium> modelList);

    @Query("SELECT * FROM bookPremium")
    List<BookPremium> getBooksPremium();

    @Query("SELECT id FROM bookPremium where id = :bookId")
    int getThisBookPurchase(int bookId);

    @Query("DELETE FROM bookPremium")
    void deleteBookPremium();
}
