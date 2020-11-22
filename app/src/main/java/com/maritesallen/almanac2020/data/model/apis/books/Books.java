package com.maritesallen.almanac2020.data.model.apis.books;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "books")
public class Books implements Parcelable {

    @PrimaryKey
    @NotNull
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    private Integer id;
    @ColumnInfo(name = "title")
    @SerializedName("title")
    @Expose
    private String title;
    @ColumnInfo(name = "file")
    @SerializedName("file")
    @Expose
    private String file;
    @ColumnInfo(name = "currency_code")
    @SerializedName("currency_code")
    @Expose
    private String currencyCode;
    @ColumnInfo(name = "price")
    @SerializedName("price")
    @Expose
    private String price;
    @ColumnInfo(name = "description")
    @SerializedName("description")
    @Expose
    private String description;
    @ColumnInfo(name = "cover_name")
    @SerializedName("cover_name")
    @Expose
    private String coverName;
    @ColumnInfo(name = "cover_image")
    @SerializedName("cover_image")
    @Expose
    private String coverImage;

    @ColumnInfo(name = "purchase_id")
    @SerializedName("purchase_id")
    @Expose
    private String purchaseId;

    @ColumnInfo(name = "cover_MainImage")
    @SerializedName("cover_MainImage")
    @Expose
    private String coverImageFlat;
    @ColumnInfo(name = "fileName")
    @SerializedName("fileName")
    @Expose
    private String fileName;

    @ColumnInfo(name = "publish_year")
    @SerializedName("publish_year")
    @Expose
    private String publishYear;

    @ColumnInfo(name = "productId")
    @SerializedName("productId")
    @Expose
    private String productId;

    @ColumnInfo(name = "drmId")
    @SerializedName("drmId")
    @Expose
    private String drmId;

    @ColumnInfo(name = "status")
    @SerializedName("status")
    @Expose
    private Integer status;

    @ColumnInfo(name = "priority")
    @SerializedName("priority")
    @Expose
    private Integer priority;

    @ColumnInfo(name = "fileNameEncrypt")
    @SerializedName("fileNameEncrypt")
    @Expose
    private String fileNameEncrypt;

    @NotNull
    public Integer getId() {
        return id;
    }

    public void setId(@NotNull Integer id) {
        this.id = id;
    }

    public String getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(String purchaseId) {
        this.purchaseId = purchaseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverName() {
        return coverName;
    }

    public void setCoverName(String coverName) {
        this.coverName = coverName;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getCoverImageFlat() {
        return coverImageFlat;
    }

    public void setCoverImageFlat(String coverImageFlat) {
        this.coverImageFlat = coverImageFlat;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(String publishYear) {
        this.publishYear = publishYear;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getDrmId() {
        return drmId;
    }

    public void setDrmId(String drmId) {
        this.drmId = drmId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Books(){}

    protected Books(Parcel in) {
        id = in.readInt();
        title = in.readString();
        file = in.readString();
        currencyCode = in.readString();
        price = in.readString();
        description = in.readString();
        coverName = in.readString();
        coverImage = in.readString();
        coverImageFlat = in.readString();
        fileName = in.readString();
        publishYear = in.readString();
        productId = in.readString();
        drmId = in.readString();
        fileNameEncrypt = in.readString();
        status = in.readInt();
        priority = in.readInt();
    }

    public static final Creator<Books> CREATOR = new Creator<Books>() {
        @Override
        public Books createFromParcel(Parcel in) {
            return new Books(in);
        }

        @Override
        public Books[] newArray(int size) {
            return new Books[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(file);
        dest.writeString(currencyCode);
        dest.writeString(price);
        dest.writeString(description);
        dest.writeString(coverName);
        dest.writeString(coverImage);
        dest.writeString(coverImageFlat);
        dest.writeString(fileName);
        dest.writeString(productId);
        dest.writeString(publishYear);
        dest.writeInt(status);
        dest.writeInt(priority);
        dest.writeString(drmId);
        dest.writeString(fileNameEncrypt);
    }

    public String getFileNameEncrypt() {
        return fileNameEncrypt;
    }

    public void setFileNameEncrypt(String fileNameEncrypt) {
        this.fileNameEncrypt = fileNameEncrypt;
    }
}
