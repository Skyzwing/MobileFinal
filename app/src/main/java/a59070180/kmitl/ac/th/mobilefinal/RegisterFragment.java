package a59070180.kmitl.ac.th.mobilefinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterFragment extends Fragment {

    private EditText _username, _name, _age, _password;
    private String _usernamestr, _nameStr, _passwordStr;
    private int _ageInt;
    private Button button;
    private SQLiteDatabase database;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{

            database = getActivity().openOrCreateDatabase("my.db", Context.MODE_PRIVATE, null);

        }catch (Exception e){
            Log.d("final", e.getMessage());
        }database.execSQL("CREATE TABLE IF NOT EXISTS user (id INTEGER PRIMARY KEY AUTOINCREMENT, userId VARCHAR(12), name VARCHAR(50), age INTEGER, password VARCHAR(30))");
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRegisterBtn();
    }

    private void initRegisterBtn(){
        _username = getView().findViewById(R.id.username_register);
        _name = getView().findViewById(R.id.name_register);
        _age = getView().findViewById(R.id.ageRegister);
        _password = getView().findViewById(R.id.password_register);
        button = getView().findViewById(R.id.button_register);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _ageInt = 0;
                _usernamestr = _username.getText().toString();
                _nameStr = _name.getText().toString();
                _passwordStr = _password.getText().toString();
                if(_usernamestr.length() < 6 && _usernamestr.length()>12){
                    Toast.makeText(getActivity(), "username must be 6 - 12 character.", Toast.LENGTH_LONG).show();
                    Log.d("final", "username register");
                }
                else if(_nameStr.contains(" ")){
                    Toast.makeText(getActivity(), "name must have white space", Toast.LENGTH_LONG).show();
                    Log.d("final", "name register");
                }
                try{
                    if (_ageInt <10 && _ageInt > 80 ){
                        _ageInt = Integer.parseInt(_age.getText().toString());
                        Toast.makeText(getActivity(), "age must be 10 - 80 year old.", Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    Log.d("final", "age register: " +e.getMessage());
                }
                if(_passwordStr.length() < 6){
                    Toast.makeText(getActivity(), "password must have 6 character or more", Toast.LENGTH_LONG).show();
                }
                else {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("userId", _usernamestr);
                    contentValues.put("name", _nameStr);
                    contentValues.put("age", _ageInt);
                    contentValues.put("password", _passwordStr);
                    database.insert("user", null, contentValues);
                    Toast.makeText(getActivity(), "Register success. Go to login.", Toast.LENGTH_LONG).show();
                    getFragmentManager().beginTransaction().replace(R.id.main_view, new LoginFragment()).addToBackStack(null).commit();
                    Log.d("final", "register success");
                }

            }
        });
//        _username = getView().findViewById(R.id.username_register);
//        _name = getView().findViewById(R.id.name_register);
//        _age = getView().findViewById(R.id.ageRegister);
//        _password = getView().findViewById(R.id.password_register);
//        _usernamestr = _username.getText().toString();
//        _nameStr = _name.getText().toString();
//        _passwordStr = _password.getText().toString();
//        button = getView().findViewById(R.id.button_register);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int ageInt = 0;
//                String message = "";
//                boolean success = true;
//
//                if (_usernamestr.length()< 6 || _usernamestr.length() > 12){
//                    success = false;
//                    message += "User ID lenght must be 6 - 12 character.";
//                }
//                if (_nameStr.length() < 3 || !_nameStr.contains(" "))
//                {
//                    success = false;
//                    message += "Name must have firstname and lastname seperated by space\n";
//                    Log.d("final", "name condition incorrect");
//                }
//                try
//                {
//                    if (ageInt < 10 || ageInt > 80)
//                    {
//                        _ageInt= Integer.parseInt(_age.getText().toString());
//                        success = false;
//                        message += "age must be 10 - 80\n";
//                        Log.d("final", "age condition incorrect");
//                    }
//                }
//                catch (Exception e)
//                {
//                    success = false;
//                    message += "age must be number\n";
//                    Log.d("final", "age condition incorrect");
//                }
//                if (_passwordStr.length() <= 6)
//                {
//                    success = false;
//                    message += "password must be longer than 6 character";
//                    Log.d("final", "password condition incorrect");
//                }
//                if (!success)
//                {
//                    Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
//                    Log.d("final", "register failed :\n" + message);
//                }
//                else{
//                    ContentValues contentValues = new ContentValues();
//                    contentValues.put("userId", _usernamestr);
//                    contentValues.put("name", _nameStr);
//                    contentValues.put("age", _ageInt);
//                    contentValues.put("password", _passwordStr);
//                    database.insert("user", null, contentValues);
//                    Toast.makeText(getActivity(), "Register success. Go to login.", Toast.LENGTH_LONG).show();
//                    getFragmentManager().beginTransaction().replace(R.id.main_view, new LoginFragment()).addToBackStack(null).commit();
//                    Log.d("final", "register success");
//                }
//            }
//        });
    }
}
