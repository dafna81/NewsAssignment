package cohen.dafna.newsassignment.utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicBoolean;

public class SingleLiveData<T> extends MutableLiveData<T> {
    private AtomicBoolean isNew;

    @Override
    public void postValue(T value) {
        isNew = new AtomicBoolean(true);
        super.postValue(value);
    }

    @Override
    public void observe(@NonNull @NotNull LifecycleOwner owner, @NonNull @NotNull Observer<? super T> observer) {
        super.observe(owner, t -> {
            if (isNew.get()){
                isNew.set(false);
                observer.onChanged(t);
            }
        });
    }
}