package com.juraganpisang.sinaujowo;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.juraganpisang.sinaujowo.databinding.FragmentAccountBinding;
import com.juraganpisang.sinaujowo.databinding.FragmentCategoryBinding;

public class AccountFragment extends Fragment {

    private LinearLayout logoutB, leaderB, profileB, bookmarkB;
    private TextView profile_img_text, name, score, rank;
    private BottomNavigationView bottomNavigationView;

    public AccountFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//         Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        initViews(view);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Akun");

        String username = DBQuery.myProfile.getName();
        profile_img_text.setText(username.toUpperCase().substring(0,1));

        name.setText(username);

        score.setText(String.valueOf(DBQuery.myPerfomance.getScore()));



        logoutB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();

                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.client_id))
                        .requestEmail()
                        .build();

                GoogleSignInClient mGoogleClient = GoogleSignIn.getClient(getContext(), gso);

                mGoogleClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                        getActivity().finish();
                    }
                });
            }
        });

        bookmarkB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        profileB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MyProfileActivity.class);
                startActivity(intent);
            }
        });

        leaderB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomNavigationView.setSelectedItemId(R.id.nav_leaderboard_bottom);
            }
        });

        return view;
    }

    private void initViews(View view){
        logoutB = view.findViewById(R.id.logoutB);
        leaderB = view.findViewById(R.id.leaderB);
        profileB = view.findViewById(R.id.profileB);
        bookmarkB = view.findViewById(R.id.bookmarkB);

        profile_img_text = view.findViewById(R.id.profile_img_text);
        name = view.findViewById(R.id.name);
        score = view.findViewById(R.id.total_score);
        rank = view.findViewById(R.id.ranking);

        bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}