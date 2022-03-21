package com.example.quizheist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.quizheist.Constants.Companion.quizAmount
import kotlinx.android.synthetic.main.buying_dialog.view.*

class BuyingDialog : DialogFragment(R.layout.buying_dialog){
    companion object {

        fun newInstance(amount:Int): BuyingDialog {
            val args = Bundle()
            args.putInt("amount",amount)
            val fragment = BuyingDialog()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners(view)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    private fun setupClickListeners(view: View) {
        view.confirm_button.setOnClickListener {
            // TODO: Do some task here
            quizAmount = arguments?.getInt("amount")!!
            Toast.makeText(activity,"Now you have "+ quizAmount+" questions per quiz",Toast.LENGTH_SHORT).show()
            dismiss()
        }
        view.cancel_button.setOnClickListener {
            // TODO: Do some task here
            Toast.makeText(activity,"Cancelled",Toast.LENGTH_SHORT).show()
            dismiss()
        }
    }
}