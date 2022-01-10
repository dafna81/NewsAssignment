package cohen.dafna.newsassignment.firebase.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import cohen.dafna.newsassignment.models.Article;

public class FavoritesRepository {

    private DatabaseReference favoritesRef = RealtimeDB.root.child("users").child(FirebaseAuthentication.getUser().getUid()).child("favorites");
    private MutableLiveData<List<Article>> userFavoritesLiveData;
    private MutableLiveData<Exception> exceptionLiveData;

    private ValueEventListener userFavoritesListener;

    public FavoritesRepository() {
        userFavoritesLiveData = new MutableLiveData<>();
        exceptionLiveData = new MutableLiveData<>();
        userFavoritesListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Article> articles = new ArrayList<>();
                for (DataSnapshot child : snapshot.getChildren()) {
                    Article article = child.getValue(Article.class);
                    articles.add(article);
                }
                userFavoritesLiveData.postValue(articles);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                exceptionLiveData.postValue(error.toException());
            }
        };
        favoritesRef.addValueEventListener(userFavoritesListener);
    }

    public void addArticleToFavorites(Article article) {

        favoritesRef.get()
                .addOnSuccessListener(dataSnapshot -> {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        Article fav = child.getValue(Article.class);
                        if (fav == null) {
                            continue;
                        }
                        if (fav.equals(article)) {
                            return;
                        }
                    }
                    favoritesRef.push()
                            .setValue(article)
                            .addOnFailureListener(e -> {
                                exceptionLiveData.postValue(e);
                            });
                }).addOnFailureListener(e -> {
            exceptionLiveData.postValue(e);
        });
    }

    public void deleteArticleFromFavorites(Article todelete) {
        favoritesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String id = todelete.getUrl();
                for (DataSnapshot child : snapshot.getChildren()) {
                    Article article = child.getValue(Article.class);
                    if (article == null)
                        continue;
                    if (article.getUrl().equals(id)) {
                        child.getRef().removeValue().addOnFailureListener(e -> exceptionLiveData.postValue(e));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                exceptionLiveData.postValue(error.toException());
            }
        });
    }


    public void populateFavorites(ArrayList<Article> articleList) {
        favoritesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap<String, Boolean> map = new HashMap<>();

                for (DataSnapshot child : snapshot.getChildren()) {
                    Article article = child.getValue(Article.class);
                    if (article == null)
                        continue;
                    map.put(article.getTitle(), true);
                }

                articleList.forEach(article -> {
                    if (map.get(article.getTitle()) != null && map.get(article.getTitle())) {
                        article.setLiked(true);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                exceptionLiveData.postValue(error.toException());
            }
        });
    }

    public LiveData<List<Article>> getUserFavoritesLiveData() {
        return userFavoritesLiveData;
    }

    public MutableLiveData<Exception> getExceptionLiveData() {
        return exceptionLiveData;
    }

    public void dispose() {
        favoritesRef.removeEventListener(userFavoritesListener);
    }


}
