package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.CheckBox
import android.widget.ExpandableListView
import android.widget.TextView
import com.perfect.prodsuit.Model.ModelProjectCheckList
import com.perfect.prodsuit.Model.ModelProjectCheckListSub
import com.perfect.prodsuit.R

class SiteCheckListAdapter (private val context: Context, private val parentItems: List<ModelProjectCheckList>) :
    BaseExpandableListAdapter() {

    var TAG = "SiteCheckListAdapter"
    override fun getGroupCount(): Int {
        return parentItems.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return parentItems[groupPosition].SubArrary.size
    }

    override fun getGroup(groupPosition: Int): Any {
        return parentItems[groupPosition]
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return parentItems[groupPosition].SubArrary[childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.adapter_site_parent, parent, false)

        val parentItem = getGroup(groupPosition) as ModelProjectCheckList
        val textView = view.findViewById<TextView>(R.id.tv_parent_label)
        val chk_box_Parent = view.findViewById<CheckBox>(R.id.chk_box_Parent)
        textView.text = parentItem.CLTyName
        if (parentItem.is_checked){
            chk_box_Parent.isChecked = true
        }else{
            chk_box_Parent.isChecked = false
        }


        chk_box_Parent.setOnClickListener {
            if (chk_box_Parent.isChecked){
                parentItem.is_checked = true
                (parent as ExpandableListView).expandGroup(groupPosition)
                for (k in 0 until parentItem.SubArrary.size) {
                    parentItem.SubArrary[k].is_checked= true
                }

                notifyDataSetChanged()

            }else{
                parentItem.is_checked = false
                (parent as ExpandableListView).collapseGroup(groupPosition)
                for (k in 0 until parentItem.SubArrary.size) {
                    parentItem.SubArrary[k].is_checked= false
                }

                notifyDataSetChanged()
            }
        }
        textView.setOnClickListener {
            if (isExpanded) {
                // Collapse the group if it's already expanded
                (parent as ExpandableListView).collapseGroup(groupPosition)
            } else {
                // Expand the group if it's collapsed
                (parent as ExpandableListView).expandGroup(groupPosition)
            }
        }

        return view
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.adapter_site_child, parent, false)

        val childItem = getChild(groupPosition, childPosition) as ModelProjectCheckListSub
        val textView = view.findViewById<TextView>(R.id.tv_child_label)
        val chk_box_child = view.findViewById<CheckBox>(R.id.chk_box_child)

        Log.e(TAG,"1099   "+groupPosition+"  :  "+childPosition)
        chk_box_child.isChecked = childItem.is_checked
        textView.text = childItem.CkLstName

        chk_box_child.setOnClickListener {
            if (chk_box_child.isChecked){
                Log.e(TAG,"13221 Child  hasCheck     ")
                parentItems[groupPosition].SubArrary[childPosition].is_checked = true

            }else{
                Log.e(TAG,"13222 Child  hasCheck     ")
                parentItems[groupPosition].SubArrary[childPosition].is_checked  = false
            }

            var hasCheck =  hasChildChecked(parentItems[groupPosition].SubArrary)
            Log.e(TAG,"13223 Child  hasCheck     "+hasCheck)
            if (hasCheck){
                parentItems[groupPosition].is_checked = true
            }else{
                parentItems[groupPosition].is_checked = false
            }
            notifyDataSetChanged()
        }

        return view
    }

    private fun hasChildChecked(subArray: Array<ModelProjectCheckListSub>): Boolean {
        var isChecked = false
        for (i in 0 until subArray.size) {
            Log.e(TAG,"13224 Child  hasCheck     "+subArray.get(i).is_checked)
            if (subArray.get(i).is_checked){
                isChecked = true
                break
            }
        }

        return isChecked
    }
}