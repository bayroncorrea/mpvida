package com.example.m_p_v.Controlador_chat;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;

import com.example.cookie.R;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<Message> messages;
    private String currentUserId;
    private Context context;

    // Constructor actualizado para recibir el Context
    public MessageAdapter(Context context, List<Message> messages, String currentUserId) {
        this.context = context;
        this.messages = messages;
        this.currentUserId = currentUserId;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = messages.get(position);

        // Asignar el texto del mensaje
        if (message.getSender().equals(currentUserId)) {
            // Mensaje enviado
            holder.layoutSentMessage.setVisibility(View.VISIBLE);
            holder.layoutReceivedMessage.setVisibility(View.GONE);
            holder.textSentMessage.setText(message.getText());
            holder.textSentTimestamp.setText(formatTimestamp(message.getTimestamp()));
        } else {
            // Mensaje recibido
            holder.layoutReceivedMessage.setVisibility(View.VISIBLE);
            holder.layoutSentMessage.setVisibility(View.GONE);
            holder.textReceivedMessage.setText(message.getText());
            holder.textReceivedTimestamp.setText(formatTimestamp(message.getTimestamp()));
        }

        // Mostrar el mensaje al que se está respondiendo si existe
        if (message.getReplyTo() != null) {
            holder.replyTextView.setVisibility(View.VISIBLE);
            holder.replyTextView.setText("Replying to: " + message.getReplyTo());
        } else {
            holder.replyTextView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layoutSentMessage;
        LinearLayout layoutReceivedMessage;
        TextView textSentMessage;
        TextView textSentTimestamp;
        TextView textReceivedMessage;
        TextView textReceivedTimestamp;
        TextView replyTextView;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutSentMessage = itemView.findViewById(R.id.layoutSentMessage);
            layoutReceivedMessage = itemView.findViewById(R.id.layoutReceivedMessage);
            textSentMessage = itemView.findViewById(R.id.textSentMessage);
            textSentTimestamp = itemView.findViewById(R.id.textSentTimestamp);
            textReceivedMessage = itemView.findViewById(R.id.textReceivedMessage);
            textReceivedTimestamp = itemView.findViewById(R.id.textReceivedTimestamp);
            replyTextView = itemView.findViewById(R.id.textViewReply);
        }
    }

    private String formatTimestamp(long timestamp) {
        java.text.DateFormat dateFormat = DateFormat.getTimeFormat(context);
        return dateFormat.format(new java.util.Date(timestamp));
    }
}