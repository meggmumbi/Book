package com.mumbi.book;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BookViewHolder> {

    ArrayList<Book> books;
    public BooksAdapter(ArrayList<Book> books){
        this.books = books;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.book_list_item, parent, false);
        return new BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
         Book book = books.get(position);
         holder.bind(book);

    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvTitle;
        TextView tvAuthors;
        TextView tvDate;
        TextView tvPublisher;

        public BookViewHolder(View itemView){
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.title);
            tvAuthors = (TextView) itemView.findViewById(R.id.author);
            tvDate = (TextView) itemView.findViewById(R.id.publisherDate);
            tvPublisher = (TextView)itemView.findViewById(R.id.publisher);
            itemView.setOnClickListener(this);

        }
        public void bind (Book book){
            tvTitle.setText(book.title);


            tvAuthors.setText(book.authors);
            tvDate.setText(book.publishedDate);
            tvPublisher.setText(book.publisher);
        }


        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Book selectedBook = books.get(position);
            Intent intent = new Intent(view.getContext(), BookDetail.class);
            intent.putExtra("Book",  selectedBook);
            view.getContext().startActivity(intent);

        }
    }
}
