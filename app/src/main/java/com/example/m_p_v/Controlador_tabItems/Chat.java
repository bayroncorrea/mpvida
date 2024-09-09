package com.example.m_p_v.Controlador_tabItems;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.example.cookie.R;
import com.example.m_p_v.Controlador_chat.Message;
import com.example.m_p_v.Controlador_chat.MessageAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

// ChatFragment.java
public class Chat extends Fragment {

    private RecyclerView recyclerView;
    private EditText editTextMessage;
    private Button buttonSend;
    private MessageAdapter messageAdapter;
    private List<Message> messages;
    private DatabaseReference databaseReference;
    private ValueEventListener messageListener;
    private String replyToMessageId = null;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewMessages);
        editTextMessage = view.findViewById(R.id.editTextMessage);
        buttonSend = view.findViewById(R.id.buttonSend);

        messages = new ArrayList<>();
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        messageAdapter = new MessageAdapter(getContext(), messages, currentUserId); // Pasar el contexto aquí
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(messageAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("conversations").child("conversationId1").child("messages");

        // Configura el ValueEventListener para actualizar la lista de mensajes
        messageListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messages.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Message message = snapshot.getValue(Message.class);
                    if (message != null) {
                        messages.add(message);
                    }
                }
                messageAdapter.notifyDataSetChanged(); // Notifica al adaptador de los cambios
                recyclerView.scrollToPosition(messages.size() - 1); // Desplaza hacia el último mensaje
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Maneja errores en la lectura de datos
            }
        };

        databaseReference.addValueEventListener(messageListener);

        buttonSend.setOnClickListener(v -> sendMessage());

        return view;
    }

    private void sendMessage() {
        String messageText = editTextMessage.getText().toString().trim();
        if (!messageText.isEmpty()) {
            String messageId = databaseReference.push().getKey();
            if (messageId != null) {
                Message newMessage = new Message(
                        FirebaseAuth.getInstance().getCurrentUser().getUid(),
                        messageText,
                        System.currentTimeMillis(),
                        replyToMessageId
                );
                databaseReference.child(messageId).setValue(newMessage);
            }
            editTextMessage.setText("");
            replyToMessageId = null; // Resetear después de enviar el mensaje
        }
    }

    // Este método permite establecer el ID del mensaje al que se está respondiendo
    public void setReplyToMessageId(String messageId) {
        this.replyToMessageId = messageId;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (messageListener != null) {
            databaseReference.removeEventListener(messageListener);
        }
    }
}
