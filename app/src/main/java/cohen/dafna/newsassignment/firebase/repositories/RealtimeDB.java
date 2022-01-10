package cohen.dafna.newsassignment.firebase.repositories;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RealtimeDB {
    public static final DatabaseReference root = FirebaseDatabase.getInstance().getReference();

}
