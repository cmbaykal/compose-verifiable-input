package com.example.composeverifiableinput.base

import androidx.compose.ui.graphics.Color

inline val String.color get() = Color(android.graphics.Color.parseColor(this))