package cohen.dafna.newsassignment.firebase.repositories;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseAuthentication {
    private static final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public static FirebaseUser getUser() {
        return user;
    }
}
