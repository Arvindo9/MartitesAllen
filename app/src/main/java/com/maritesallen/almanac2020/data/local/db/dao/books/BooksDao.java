package com.maritesallen.almanac2020.data.local.db.dao.books;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.maritesallen.almanac2020.data.model.apis.books.Books;

import java.util.List;

/**
 * Author : Arvindo Mondal
 * Created on 24-01-2020
 */
@Dao
public interface BooksDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Books model);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Books> modelList);

    @Query("SELECT * FROM books order by priority asc")
    List<Books> getBooks();
}
