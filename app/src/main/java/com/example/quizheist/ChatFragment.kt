package com.example.quizheist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizheist.Constants.Companion.getDate
import com.example.quizheist.Constants.Companion.getTime
import com.example.quizheist.adapters.ChatAdapter
import com.example.quizheist.model.Messages
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_chat.*

class ChatFragment : Fragment() {
    private lateinit var rv: ChatAdapter
    private lateinit var dbReference : DatabaseReference
    private lateinit var messageList : ArrayList<Messages>
    var senderUid = ""


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        messageList = arrayListOf()
        senderUid = allData().uid.toString()

        send.setOnClickListener {
            val message = messageBox.text.toString()

            val messageObject = Messages(message,getTime(),senderUid,getDate())

            if (message.isNotEmpty()) {
                Log.d("tagetmessage",messageObject.message.toString() + " " + messageObject.senderId.toString() + " " + messageObject.time.toString())
                dbReference.child("chats").child(senderUid+"quizHeist"!!).child("messages").push()
                    .setValue(messageObject).addOnCompleteListener {
                        dbReference.child("chats").child("quizHeist"+senderUid!!).child("messages").push()
                            .setValue(messageObject)
                    }
                messageBox.setText("")
            } else {
                Toast.makeText(
                    activity,
                    "Please enter some message! ",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        getData()

    }
    fun allData():User{
        val u = User()
        Constants.dbReferenceUsers.child(activity?.intent?.getStringExtra("uid").toString()).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                u.uid = user!!.uid!!
                Log.d("taget",user.toString())
                u.name = user!!.name.toString()
                u.email = user!!.email.toString()
                u.phone = user!!.phone.toString()

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        return u
    }
    fun getData(){
        dbReference.child("chats").child(senderUid+"quizHeist"!!).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(snap in snapshot.children){
                    val m = snap.getValue(Messages::class.java)
                    messageList.add(m!!)
                }
                setupRv(messageList)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    fun setupRv(ls:ArrayList<Messages>){
        rv = ChatAdapter(requireContext(),ls)
        recyclerView1.apply{
            adapter = rv
            layoutManager = LinearLayoutManager(activity)
        }
    }
}