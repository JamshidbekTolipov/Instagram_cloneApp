package uz.jama.bottomnav.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hendraanggrian.appcompat.widget.SocialTextView;
import com.squareup.picasso.Picasso;

import java.util.List;

import uz.jama.bottomnav.Fragments.HomeFragment;
import uz.jama.bottomnav.Model.Post;
import uz.jama.bottomnav.Model.User;
import uz.jama.bottomnav.R;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private Context mContext;
    private List<Post> mPosts;

    private FirebaseUser firebaseUser;

    public PostAdapter(Context mContext, List<Post> mPosts) {
        this.mContext = mContext;
        this.mPosts = mPosts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.post_item, parent, false);

        return new PostAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        Post post = mPosts.get(position);
        Glide
                .with(mContext)
                .load(post.getImageurl())
                .into(holder.post_image);
        holder.description.setText(post.getDescription());

        FirebaseDatabase.getInstance().getReference().child("User").child(post.getPublisher()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                if (user.getImageurl().equals("default")) {
                    holder.imageProfile.setImageResource(R.mipmap.ic_launcher);
                }
                Picasso.get().load(user.getImageurl()).into(holder.imageProfile);
                holder.username.setText(user.getUsername());
                holder.author.setText(user.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageProfile;
        public ImageView post_image;
        public ImageView like;
        public ImageView comment;
        public ImageView save;
        public ImageView more;

        public TextView username;
        public TextView noOfLikes;
        public TextView author;
        public TextView noOfComments;

        SocialTextView description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageProfile = itemView.findViewById(R.id.profile_image_hm);
            post_image = itemView.findViewById(R.id.post_image_hm);
            like = itemView.findViewById(R.id.like_hm);
            comment = itemView.findViewById(R.id.comment_hm);
            save = itemView.findViewById(R.id.save_hm);
            more = itemView.findViewById(R.id.more_hm);

            username = itemView.findViewById(R.id.username_hm);
            noOfLikes = itemView.findViewById(R.id.no_of_likes_hm);
            author = itemView.findViewById(R.id.author_hm);
            noOfComments = itemView.findViewById(R.id.no_of_comments_hm);
            description = itemView.findViewById(R.id.description_hm);

        }
    }

}
