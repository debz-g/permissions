package com.debz.permissions

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import com.debz.permissions.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPerms.setOnClickListener(){
            reqPerms()
        }
    }

    private fun checkWriteStoragePerms() =
//      Two types of perms: Sensitive and Non-sensitive. Location,Storage ones are sensitive and need to be explicitly asked from the user.
        ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

/*      checkSelfPermission function is used to check whether permissions being asked from user is given or not.
        This comes under the library of ActivityCompat. The function takes this as context.

        To access the permissions we mention Manifest(android and not java) bcz that's where all the perms are stored.
        then .permission.WRITE_EXTERNAL_STORAGE

        ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
        Now this whole line return an integer that tells us whether the permission was accepted or rejected which can be checked.

        ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED

       This checks whether the checkSelfPermission() returns Permission Granted or not
 */

    private fun checkAccessCoarseLocationPerms() =
        ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

    private fun checkAccessBGLocationPerms() =
        ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

    private fun reqPerms() {
/*  We need a function to request perms. Whenever we are requesting perms, we always need to define the function as array.
    What this function does is, it check which permissions are denied and the groups them and then requests them from the user again.
 */
        var PermsToReq = mutableListOf<String>()
//      This line stores all the unrequested Permission in the PermsToReq List
        if(!checkWriteStoragePerms())
             PermsToReq.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if(!checkAccessCoarseLocationPerms())
            PermsToReq.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        if(!checkAccessBGLocationPerms())
            PermsToReq.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)

//        These above lines will add the perms denied at first.

        if(PermsToReq.isNotEmpty()){
            ActivityCompat.requestPermissions(this, PermsToReq.toTypedArray(), 0 )
//            PermsToReq.toTypedArray() is used to convert the list to array for the function to access it
        }

    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==0 && grantResults.isNotEmpty()){
            for(i in grantResults.indices){
                if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
                    Log.d("Permissions Requested", "${permissions[i]} granted.")
                }
            }

        }
    }

}