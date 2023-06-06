package com.speer.test.github.follow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.speer.test.R
import com.speer.test.databinding.FollowItemBinding
import com.speer.test.github.UserInfo
import com.squareup.picasso.Picasso

/**
 * Created by Charles Raj I on 05/06/23.
 * @author Charles Raj I
 */
class FollowAdapter constructor(var userList: List<UserInfo>) : RecyclerView.Adapter<FollowAdapter.FollowViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowViewHolder {
        val followItemBinding: FollowItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.follow_item,parent,false)
        return FollowViewHolder(followItemBinding)
    }

    override fun getItemCount(): Int  = userList.size

    override fun onBindViewHolder(holder: FollowViewHolder, position: Int) {

        holder.onBind(userList.get(position))
    }


    class FollowViewHolder(val followItemBinding: FollowItemBinding) : RecyclerView.ViewHolder(followItemBinding.root) {

        fun onBind(userInfo: UserInfo) {
            followItemBinding.userInfo = userInfo

            userInfo.avatarUrl?.let {
                Picasso.get()
                    .load(it)
                    .into(followItemBinding.userImage)
            }
        }
    }
}