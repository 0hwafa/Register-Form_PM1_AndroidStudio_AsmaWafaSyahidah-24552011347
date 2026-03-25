package com.formlog_in.tugasp5_registrasi;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    // Deklarasi Komponen
    private TextInputEditText etUsername, etEmail, etPassword, etConfirmPassword;
    private TextInputLayout tilEmail, tilConfirmPassword;
    private RadioGroup rgGender;
    private CheckBox cbCoding, cbGaming, cbReading;
    private Spinner spinnerCity;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Hubungkan variabel dengan ID di XML
        initViews();

        // 2. Setup Spinner (Poin 04)
        setupSpinner();

        // 3. Logika Klik Tombol Submit (Poin 02 & 04)
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFormValid()) {
                    showConfirmDialog();
                }
            }
        });

        // 4. Logika Long Press pada Tombol (Poin 05)
        btnSubmit.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                resetForm();
                Toast.makeText(MainActivity.this, "Form di-reset (Gesture Long Press)", Toast.LENGTH_SHORT).show();
                return true; // return true agar tidak dianggap klik biasa
            }
        });
    }

    private void initViews() {
        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        tilEmail = findViewById(R.id.tilEmail);
        tilConfirmPassword = findViewById(R.id.tilConfirmPassword);
        rgGender = findViewById(R.id.rgGender);
        cbCoding = findViewById(R.id.cbCoding);
        cbGaming = findViewById(R.id.cbGaming);
        cbReading = findViewById(R.id.cbReading);
        spinnerCity = findViewById(R.id.spinnerCity);
        btnSubmit = findViewById(R.id.btnSubmit);
    }

    private void setupSpinner() {
        String[] cities = {"Jakarta", "Bandung", "Surabaya", "Medan", "Makassar"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, cities);
        spinnerCity.setAdapter(adapter);
    }

    // Poin 02: Advanced Validation
    private boolean isFormValid() {
        String email = etEmail.getText().toString().trim();
        String pass = etPassword.getText().toString();
        String confirmPass = etConfirmPassword.getText().toString();

        // Validasi Email tidak kosong & format benar
        if (email.isEmpty()) {
            etEmail.setError("Email tidak boleh kosong");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Format email salah");
            return false;
        }

        // Validasi Password Match
        if (!pass.equals(confirmPass)) {
            etConfirmPassword.setError("Password tidak cocok!");
            return false;
        }

        // Poin 03: Validasi Selection (Minimal 1 Hobi)
        if (!cbCoding.isChecked() && !cbGaming.isChecked() && !cbReading.isChecked()) {
            Toast.makeText(this, "Pilih minimal satu hobi!", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validasi Jenis Kelamin
        if (rgGender.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Pilih jenis kelamin!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    // Poin 04: AlertDialog
    private void showConfirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Konfirmasi Data");
        builder.setMessage("Apakah Anda yakin ingin mendaftar dengan data ini?");
        builder.setPositiveButton("Ya, Daftar", (dialog, which) -> {
            Toast.makeText(MainActivity.this, "Registrasi Berhasil!", Toast.LENGTH_LONG).show();
        });
        builder.setNegativeButton("Batal", null);
        builder.show();
    }

    // Fungsi untuk reset form (dipakai di Long Press)
    private void resetForm() {
        etUsername.setText("");
        etEmail.setText("");
        etPassword.setText("");
        etConfirmPassword.setText("");
        rgGender.clearCheck();
        cbCoding.setChecked(false);
        cbGaming.setChecked(false);
        cbReading.setChecked(false);
    }
}
