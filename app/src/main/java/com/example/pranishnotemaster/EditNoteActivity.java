package com.example.pranishnotemaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

public class EditNoteActivity extends AppCompatActivity {
    private EditText editTextTitle;
    private EditText editTextContent;
    DBHelper dbHelper;

    private Button btnMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        String title = getIntent().getStringExtra("title");
        String content = getIntent().getStringExtra("content");

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextContent = findViewById(R.id.editTextContent);

        editTextTitle.setText(title);
        editTextContent.setText(content);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit_note, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
            String title = editTextTitle.getText().toString();
            String content = editTextContent.getText().toString();
            int noteId = getIntent().getIntExtra("id",0);

            dbHelper = new DBHelper(EditNoteActivity.this);
            dbHelper.updateNote(noteId, title, content);

            onBackPressed();
            return true;
        } else if (id == R.id.action_delete) {
            int noteId = getIntent().getIntExtra("id",0);

            dbHelper = new DBHelper(EditNoteActivity.this);
            dbHelper.deleteNote(noteId);

            onBackPressed();
            return true;
        } else if (id == R.id.action_back) {
            // Handle the Up button in the ActionBar
            onBackPressed();
            return true;
        }
        return false;
    }
}