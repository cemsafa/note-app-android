package com.cemsafa.note_crrk_android;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.widget.SearchView;

import com.cemsafa.note_crrk_android.Model.Folder;
import com.cemsafa.note_crrk_android.Model.Note;
import com.cemsafa.note_crrk_android.Model.NoteViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.nambimobile.widgets.efab.ExpandableFabLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class NoteActivity extends AppCompatActivity implements NoteRVAdapter.OnNoteClickListener, View.OnClickListener, SearchView.OnQueryTextListener {

    public static final String NOTE_ID = "note_id";
    private static final int REQUEST_CODE = 1;

    private NoteViewModel noteViewModel;
    private RecyclerView recyclerView;
    private NoteRVAdapter adapter;

    private String folderName;
    private long folderId = 0;
    private boolean isAsc;
    private double latitude = 0.0;
    private double longitude = 0.0;

    private List<Note> noteList;

    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;

    private static final int UPDATE_INTERVAL = 5000; // 5 seconds
    private static final int FASTEST_INTERVAL = 3000; // 3 seconds

    private List<String> permissionsToRequest;
    private List<String> permissions = new ArrayList<>();
    private List<String> permissionsRejected = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);

        permissionsToRequest = permissionsToRequest(permissions);
        if (permissionsToRequest.size() > 0) {
            requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), REQUEST_CODE);
        }

        noteViewModel = new ViewModelProvider.AndroidViewModelFactory(this.getApplication()).create(NoteViewModel.class);

        recyclerView = findViewById(R.id.rvNotes);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (getIntent().hasExtra(FolderActivity.FOLDER_NAME)) {
            folderName = getIntent().getStringExtra(FolderActivity.FOLDER_NAME);
            folderId = getIntent().getLongExtra(FolderActivity.FOLDER_ID, 0);
            noteViewModel.getNotesInFolder(folderName).observe(this, notes -> {
                adapter = new NoteRVAdapter(notes, this, this);
                recyclerView.setAdapter(adapter);
            });
        }

        ExpandableFabLayout fabLayout = findViewById(R.id.fab_layout);
        fabLayout.getPortraitConfiguration().getFabOptions().forEach(fabOption -> {
            fabOption.setOnClickListener(this);
        });
    }

    @Override
    public void onNoteClick(int position) {
        noteViewModel.getAllNotes().observe(this, notes -> {
            Intent intent = new Intent(NoteActivity.this, AddEditActivity.class);
            intent.putExtra(NOTE_ID, notes.get(position).getId());
            startActivity(intent);
        });
    }

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
            Intent data = result.getData();
            String titleReply = data.getStringExtra(AddEditActivity.TITLE_REPLY);
            String contentReply = data.getStringExtra(AddEditActivity.CONTENT_REPLY);

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CANADA);
            String createdDate = simpleDateFormat.format(calendar.getTime());

            findLocation();

            findLocation();

            Folder folder = new Folder();
            folder.setId(folderId);
            folder.setName(folderName);
            Note note = new Note(folderName, titleReply, contentReply, createdDate, latitude, longitude, null, null);
            noteViewModel.insertNoteInFolder(folder, note);
        }
    });

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fabAddOption:
                Intent intent = new Intent(NoteActivity.this, AddEditActivity.class);
                launcher.launch(intent);
            case R.id.fabDeleteOption:
            case R.id.fabMoveOption:
            case R.id.fabSortOption:
                isAsc = !isAsc;
                noteViewModel.sortNotes(isAsc).observe(this, notes -> {
                    adapter = new NoteRVAdapter(notes, this, this);
                    recyclerView.setAdapter(adapter);
                });
                adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.note_menu, menu);
        MenuItem search = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        if (s != null) {
            search(s);
        }
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if (s != null) {
            search(s);
        }
        return true;
    }

    private void search(String query) {
        adapter.getFilter().filter(query);
    }

    private List<String> permissionsToRequest(List<String> permissions) {
        ArrayList<String> results = new ArrayList<>();
        for (String perm: permissions) {
            if (!hasPermission(perm))
                results.add(perm);
        }

        return results;
    }

    private boolean hasPermission(String perm) {
        return checkSelfPermission(perm) == PackageManager.PERMISSION_GRANTED;
    }

    private void findLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        } else {
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                    }
                }
            });
        }

        startUpdateLocation();
    }

    private void startUpdateLocation() {
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                if (locationResult != null) {
                    Location location = locationResult.getLastLocation();
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                }
            }
        };

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }
}