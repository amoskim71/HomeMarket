package com.InnovaTechno.homemarket.Categories.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.InnovaTechno.homemarket.Categories.Models.Post;
import com.InnovaTechno.homemarket.Fragments.CartFragment;
import com.InnovaTechno.homemarket.Items_Detail.ItemDetails;
import com.InnovaTechno.homemarket.R;
import com.bumptech.glide.Glide;
import com.parse.ParseFile;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private Context context;
    private List <Post> posts;


    public PostAdapter(Context context, List<Post> posts ) {
        this.context = context;
        this.posts = posts;


    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.cardview_fruits_legumes, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }


    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFruits_Legumes;
        TextView tvName;
        TextView tvDevise;
        Button btnAddToCart;
        CardView cvFruits_Legumes;
        ToggleButton favorites;
        private TextView tvPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFruits_Legumes = itemView.findViewById(R.id.ivFruits_Legumes);
            tvName = itemView.findViewById(R.id.tvName);
            tvDevise = itemView.findViewById(R.id.tvDevise);
            btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
            cvFruits_Legumes = itemView.findViewById(R.id.cvFruits_Legumes);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            favorites = itemView.findViewById(R.id.favorites);

        }
        public void bind(Post post){
            ParseFile image = post.getImage();
            if (image != null) {
                Glide.with(context).load(image.getUrl()).into(ivFruits_Legumes);
            }

            tvName.setText(post.getName());
            tvDevise.setText(post.getDevise());
            tvPrice.setText(post.getPrice());

            //Passing the data to items details activity
            cvFruits_Legumes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Intent intent = new Intent(context, ItemDetails.class);
                    intent.putExtra("name", posts.get(position).getName());
                    intent.putExtra("price2", posts.get(position).getPrice());
                    intent.putExtra("devise", posts.get(position).getDevise());
                    intent.putExtra("productImage" , posts.get(position).getImage());
                    intent.putExtra("description" , posts.get(position).getDescription());
                    context.startActivity(intent);

                }
            });
            //Favorites
            favorites.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    Toast.makeText(itemView.getContext(), "Added to your favorites" + isChecked, Toast.LENGTH_SHORT).show();
                }
            });

            //passing and saving the data to FragmentCart when cliked add to cart
            btnAddToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    //Passing the data in a bundle
                    Bundle bundle = new Bundle();
                    bundle.putString("name", posts.get(position).getName());
                    bundle.putString("price2", posts.get(position).getPrice());
                    bundle.putString("devise", posts.get(position).getDevise());

                    CartFragment cartFragment = new CartFragment();
                    cartFragment.setArguments(bundle);


                   //Intent intent = new Intent(context, CartFragment.class);
                   // intent.putExtra("name", posts.get(position).getName());
                   // intent.putExtra("price2", posts.get(position).getPrice());
                   // intent.putExtra("devise", posts.get(position).getDevise());
                    //intent.putExtra("productImage", posts.get(position).getImage());
                   // context.startActivity(intent);
                }

        });

    }
    }
}
