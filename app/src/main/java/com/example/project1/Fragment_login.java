package com.example.project1;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Fragment_login extends Fragment {
    NavController navController;
    TextView txt_login, txt_register;

    EditText edit1, password1;
    Button btn_login;


    FirebaseUser currentUser;
    private FirebaseAuth fireAuth;
    FirebaseUser firebaseUser;


    public Fragment_login() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fireAuth = FirebaseAuth.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txt_login = view.findViewById(R.id.txt_login);
        edit1 = view.findViewById(R.id.edit1);
        password1 = view.findViewById(R.id.password1);
        navController = Navigation.findNavController(getActivity(), R.id.host_fragment);

        txt_register.setOnClickListener(view1 -> {
            navController.navigate(R.id.fragment_registration);
        });

        btn_login.setOnClickListener(view2 -> {

            if (!checkEmptyFields()) {
                String email = edit1.getText().toString();
                String pass = password1.getText().toString();
                login_details(email, pass);
            }

        });
    }

    private boolean checkEmptyFields() {

        if (TextUtils.isEmpty(edit1.getText().toString())) {
            edit1.setError("EmailId cannot be empty!!");
            edit1.requestFocus();
            return true;
        } else if (TextUtils.isEmpty(password1.getText().toString())) {
            password1.setError("Enter the valid password");
            password1.requestFocus();
        }
        return false;

    }


    private void login_details(String user_email, String user_psd) {

        fireAuth.signInWithEmailAndPassword(user_email, user_psd)
                .addOnCompleteListener(getActivity(), task -> {
                    if (task.isSuccessful()) {
                        firebaseUser = fireAuth.getCurrentUser();
                        Toast.makeText(getActivity().getApplicationContext(), "Log In Succesful!!", Toast.LENGTH_SHORT).show();
                        updateUI(firebaseUser);
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "Log in failed...", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("LoginFragment", "on start called!");

        firebaseUser = fireAuth.getCurrentUser();
        if (firebaseUser != null) {
            updateUI(firebaseUser);
            Toast.makeText(getActivity().getApplicationContext(), "User already signing", Toast.LENGTH_LONG).show();
        }


    }

    private void updateUI(FirebaseUser firebaseUser) {
        requireActivity().finish();
        Intent myIntent = new Intent(requireActivity(), MainActivity.class);
        myIntent.putExtra("user", firebaseUser);
        requireActivity().startActivity(myIntent);
    }



}




