package com.example.project1;

import android.app.Person;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class fragment_registration extends Fragment {
    EditText edt_email,edt_pass,edt_cpass,edt_name,edt_location, edt_dob, edt_gender;
    Button btn_register;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    private NavController navController;



    public fragment_registration() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edt_email = view.findViewById(R.id.email);
        edt_pass = view.findViewById(R.id.password);
        edt_cpass = view.findViewById(R.id.CPassword);
        edt_name = view.findViewById(R.id.name);
        edt_location = view.findViewById(R.id.location);
        edt_dob = view.findViewById(R.id.birth);
        edt_gender = view.findViewById(R.id.gender);
        btn_register = view.findViewById(R.id.join);

        navController = Navigation.findNavController(getActivity(),R.id.host_fragment);

        btn_register.setOnClickListener(view1 -> {

            if (!checkEmptyFields())
            {
                if (edt_pass.getText().length()<6)
                {
                    edt_pass.setError("Invalid Password, Password Should be at least 6 characters");
                    edt_pass.requestFocus();
                }else {
                    if (!edt_pass.getText().toString().equals(edt_cpass.getText().toString()))
                    {
                        edt_cpass.setError("Password not match!");
                        edt_cpass.requestFocus();
                    }else
                    {
                        String email = edt_email.getText().toString();
                        String pass = edt_pass.getText().toString();
                        String name = edt_name.getText().toString();
                        String location = edt_location.getText().toString();
                        String dob = edt_dob.getText().toString();
                        String gender = edt_gender.getText().toString();
                        Persons person = new Persons(email,pass,name,location,dob,gender);

                        createUser(person);

                    }
                }

            }

        });

    }

    public boolean checkEmptyFields()
    {
        if(TextUtils.isEmpty(edt_email.getText().toString()))
        {
            edt_email.setError("Email cannot be empty!");
            edt_email.requestFocus();
            return true;
        }else if (TextUtils.isEmpty(edt_pass.getText().toString()))
        {
            edt_pass.setError("Password cannot be empty!");
            edt_pass.requestFocus();
            return true;
        }else if (TextUtils.isEmpty(edt_cpass.getText().toString()))
        {
            edt_cpass.setError("Confirm Password cannot be empty!");
            edt_cpass.requestFocus();
            return true;
        }else if (TextUtils.isEmpty(edt_name.getText().toString()))
        {
            edt_name.setError("Name cannot be empty!");
            edt_name.requestFocus();
            return true;
        }else if (TextUtils.isEmpty(edt_location.getText().toString()))
        {
            edt_location.setError("Location cannot be empty!");
            edt_location.requestFocus();
            return true;
        }else if (TextUtils.isEmpty(edt_dob.getText().toString()))
        {
            edt_dob.setError("dob cannot be empty!");
            edt_dob.requestFocus();
            return true;
        }else if (TextUtils.isEmpty(edt_gender.getText().toString()))
        {
            edt_gender.setError("gender cannot be empty!");
            edt_gender.requestFocus();
            return true;
        }

        return false;
    }

    public void createUser(Persons person)
    {
        auth.createUserWithEmailAndPassword(person.getEmail(),person.getPassword())
                .addOnCompleteListener(getActivity(), task -> {

                    if (task.isSuccessful())
                    {
                        FirebaseUser firebaseUser = auth.getCurrentUser();
                        writeFireStore(person, firebaseUser);
                    }else {
                        Toast.makeText(getActivity().getApplicationContext(),"Registration Error!",Toast.LENGTH_LONG).show();
                    }

                });
    }

    public void writeFireStore(Persons person, FirebaseUser firebaseUser)
    {
        Map<String,Object> userMap = new HashMap<>();
        userMap.put("Name",person.getName());
        userMap.put("Email",person.getEmail());
        userMap.put("Location",person.getCity());
        userMap.put("birthday",person.getBirthdate());
        userMap.put("gender",person.getGender());

        firestore.collection("User").document(firebaseUser.getUid())
                .set(userMap).addOnCompleteListener(getActivity(), task -> {
            if (task.isSuccessful())
            {
                Toast.makeText(getActivity().getApplicationContext(),"Registration Success!",Toast.LENGTH_LONG).show();
                FirebaseAuth.getInstance().signOut();
                navController.navigate(R.id.fragment_login);
            }else
            {
                Toast.makeText(getActivity().getApplicationContext(),"FireStore Error!",Toast.LENGTH_LONG).show();
            }
        });

    }
}