package com.millenialzdev.myloginandregistersqlite

import android.app.Activity
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class Register : BottomSheetDialogFragment() {

    private lateinit var db: DataBaseHelperLogin

    companion object {
        const val TAG = "Register"

        fun newInstance(): Register {
            return Register()
        }
    }

    override fun onCreateView(
        @NonNull inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.register, container, false)
        return view
    }

    override fun onViewCreated(@NonNull view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username = view.findViewById<EditText>(R.id.etUsername)
        val password = view.findViewById<EditText>(R.id.etPassword)
        val repassword = view.findViewById<EditText>(R.id.etRepeatPassword)
        val daftar = view.findViewById<Button>(R.id.btnRegister)

        db = DataBaseHelperLogin(requireActivity())

        daftar.setOnClickListener {
            val inUsername = username.text.toString()
            val inPassword = password.text.toString()
            val inRePassword = repassword.text.toString()

            if (inRePassword != inPassword) {
                repassword.error = "Password Tidak Sama"
            } else {
                val daftar = db.simpanUser(inUsername, inPassword)
                if (daftar) {
                    Toast.makeText(requireActivity(), "Daftar Berhasil", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(requireActivity(), "Daftar Gagal", Toast.LENGTH_LONG).show()
                }
                dismiss()
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        val activity = activity
        if (activity is OnDialogCloseListener) {
            (activity as OnDialogCloseListener).onDialogClose(dialog)
        }
    }

    interface OnDialogCloseListener {
        fun onDialogClose(dialog: DialogInterface)
    }
}
