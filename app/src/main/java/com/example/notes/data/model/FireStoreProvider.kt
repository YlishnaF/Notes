package com.example.notes.data.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notes.extensions.MyLog
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.*

private const val NOTES_COLLECTION = "notes"

class FireStoreProvider : RemoteDataProvider {

    companion object {
        private val TAG = "${FireStoreProvider::class.java.simpleName} :"
    }

    private val db = FirebaseFirestore.getInstance()
    private val notesReference = db.collection(NOTES_COLLECTION)

    override fun subscribeToAllNotes(): LiveData<NoteResult> {
        Log.d(MyLog, "fun subscribeToAllNotes")
        val result = MutableLiveData<NoteResult>()

        notesReference.addSnapshotListener { snapshot, e ->
            if (e != null) {
                result.value = NoteResult.Error(e)
            } else if (snapshot != null) {
                val notes = mutableListOf<Note>()

                for (doc: QueryDocumentSnapshot in snapshot) {
                    notes.add(doc.toObject(Note::class.java))
                }
                result.value = NoteResult.Success(notes)
            }
        }
        return result
    }

    override fun getNoteById(id: String): LiveData<NoteResult> {
        Log.d(MyLog, "fun getNoteById")
        val result = MutableLiveData<NoteResult>()

        notesReference.document(id).get()
            .addOnSuccessListener { snapshot ->
                result.value =
                    NoteResult.Success(snapshot.toObject(Note::class.java))
            }.addOnFailureListener { result.value = NoteResult.Error(it) }
        return result
    }

    override fun saveNote(note: Note): LiveData<NoteResult> {
        Log.d(MyLog, "fun save note")
        val result = MutableLiveData<NoteResult>()
        notesReference.document(note.id)
            .set(note).addOnSuccessListener {
                Log.d(TAG, "Note $note is saved")
                result.value = NoteResult.Success(note)
            }.addOnFailureListener {
                OnFailureListener { p0 ->
                    Log.d(TAG, "Error saving note $note, message: ${p0.message}")
                    result.value = NoteResult.Error(p0)
                }
            }
        return result
    }
}