
package com.example.rakeshrav.magicmovies.data.db.model.movieData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "result")
public class Result {


    @SerializedName("id")
    @Expose
    @Id(autoincrement = true)
    private Long id;

    @SerializedName("poster_path")
    @Expose
    @Property(nameInDb = "poster_path")
    private String posterPath;

    @Expose
    @SerializedName("collectionId")
    @Property(nameInDb = "collectionId")
    private Long collectionId;

    @Generated(hash = 167387673)
    public Result(Long id, String posterPath, Long collectionId) {
        this.id = id;
        this.posterPath = posterPath;
        this.collectionId = collectionId;
    }

    @Generated(hash = 1176609929)
    public Result() {
    }

    public Long getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Long collectionId) {
        this.collectionId = collectionId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

}
