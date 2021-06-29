package es.webandroid.marvel.core.extensions

import android.app.Activity
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import es.webandroid.marvel.R

fun ImageView.loadFromUrl(url: String?) = Picasso.get().load(url).placeholder(R.drawable.ic_error_placeholder).into(this)

fun Fragment.toast(text: String) = Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()

fun Activity.toast(text: String) = Toast.makeText(this, text, Toast.LENGTH_LONG).show()
