package ramizbek.aliyev.firebasechatapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import ramizbek.aliyev.firebasechatapp.R
import ramizbek.aliyev.firebasechatapp.activity.ChatActivity
import ramizbek.aliyev.firebasechatapp.models.User

class UserAdapter(private val context: Context, private val userList: ArrayList<User>) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList[position]
        holder.txtUserName.text = user.userName
        Glide.with(context).load(user.profileImage).placeholder(R.drawable.binary_code).into(holder.imageUser)

        holder.layoutUser.setOnClickListener {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("userId", user.userId)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int = userList.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtUserName: TextView = view.findViewById(R.id.userName)
        val txtTemp: TextView = view.findViewById(R.id.temp)
        val imageUser: CircleImageView = view.findViewById(R.id.userImage)
        val layoutUser:LinearLayout = view.findViewById(R.id.layoutUser)
    }

}