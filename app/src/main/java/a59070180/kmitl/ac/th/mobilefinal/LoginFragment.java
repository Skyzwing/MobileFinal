package a59070180.kmitl.ac.th.mobilefinal;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class LoginFragment extends Fragment {

    private EditText _username, _password;
    private TextView _register;
    private SQLiteDatabase database;
    private SharedPreferences sharedPreferences;
    private String _usernameStr, _passwordStr, url;
    private ImageView imageView;
    private Button button;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        database = getActivity().openOrCreateDatabase("my.db", Context.MODE_PRIVATE, null);
        database.execSQL("CREATE TABLE IF NOT EXISTS user (id INTEGER PRIMARY KEY AUTOINCREMENT, userId VARCHAR(12), name VARCHAR(50), age INTEGER, password VARCHAR(30))");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initLoadImage();
        initLoginBtn();
        registerBtn();
    }

    private void initLoginBtn(){
        final Cursor cursor = database.rawQuery("select userId, name, age, password from user where userId = '" + _usernameStr + "' and password = '" + _passwordStr + "'", null);
        _username = getView().findViewById(R.id.username_login);
        _password = getView().findViewById(R.id.password_login);
        _usernameStr = _username.getText().toString();
        _passwordStr = _password.getText().toString();
        button = getView().findViewById(R.id.button_login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (_usernameStr.isEmpty() || _passwordStr.isEmpty()){
                    Toast.makeText(getActivity(), "Login and Password not empty.", Toast.LENGTH_LONG).show();
                    Log.d("finalExam", "Login username or password is empty");
                    return;
                }
                else if (cursor.moveToNext()){
                    sharedPreferences.edit()
                            .putString("user id", cursor.getString(0))
                            .putString("name", cursor.getString(1))
                            .putInt("age", cursor.getInt(2))
                            .putString("password", cursor.getString(3))
                            .apply();
                    getFragmentManager().beginTransaction().replace(R.id.main_view, new RegisterFragment()).commit();
                }
            }
        });
    }

    private void initLoadImage(){
        imageView = getView().findViewById(R.id.image_login);
        url = "https://www.class-central.com/bundles/classcentralsite/images/icon-programming-and-software-development.png";
        Picasso.with(getActivity()).load(url).into(imageView);
    }

    private void registerBtn(){
        _register = getView().findViewById(R.id.register_login);
        _register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.main_view, new RegisterFragment()).commit();
            }
        });
    }
}
