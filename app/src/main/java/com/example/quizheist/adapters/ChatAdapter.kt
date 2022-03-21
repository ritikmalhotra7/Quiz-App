package com.example.quizheist.adapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.BlurTransformation
import coil.transform.CircleCropTransformation
import com.example.quizheist.databinding.RecieveMessageBinding
import com.example.quizheist.databinding.SentBinding
import com.example.quizheist.model.Messages
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class ChatAdapter(val context : Context, private val messageList : ArrayList<Messages>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var storage: FirebaseStorage
    private val sentItem = 1
    private val recievedItem = 2

    class SentHolder(binding : SentBinding) : RecyclerView.ViewHolder(binding.root){
        val sentMessage = binding.sentMessage
        val txt = binding.txtsTime
        val dp = binding.profilePic2
        val date = binding.txtsdate
    }
    class RecieverHolder(binding : RecieveMessageBinding) : RecyclerView.ViewHolder(binding.root){
        val recievedMessage = binding.receiversMessage
        val txt = binding.txtrTime
        val dp = binding.profilePic1
        val date = binding.txtrdate
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == 1){
            val binding = SentBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            SentHolder(binding)
        }else{
            val binding = RecieveMessageBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            RecieverHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = messageList[position]
        if(holder.javaClass == SentHolder::class.java){
            val viewHolder = holder as SentHolder
            holder.sentMessage.text = currentMessage.message
            holder.txt.text = currentMessage.time
            holder.date.text = currentMessage.date
            storage= FirebaseStorage.getInstance()
            val uid=currentMessage.senderId

            val storagRef = storage.reference.child(uid!!).child(uid+"profile")
            holder.dp.load(storagRef.path){
                transformations(CircleCropTransformation())
            }


        }else{
            val viewHolder = holder as RecieverHolder
            holder.recievedMessage.text = currentMessage.message
            holder.txt.text = currentMessage.time
            holder.date.text = currentMessage.date
            storage= FirebaseStorage.getInstance()
            val uid=currentMessage.senderId

            val storagRef = storage.reference.child(uid!!).child(uid+"profile")
            holder.dp.load(storagRef.path){
                transformations(CircleCropTransformation())
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]
        return if(FirebaseAuth.getInstance().currentUser?.uid!! == currentMessage.senderId){
            sentItem
        }else{
            recievedItem
        }
    }
    override fun getItemCount(): Int {
        return messageList.size
    }

}