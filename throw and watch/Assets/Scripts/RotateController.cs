// Copyright (c) 2018 by Sergey Skaredov
// Licensed under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

using UnityEngine;

public class RotateController : MonoBehaviour {

    #region [Private fields]

    private bool rotateEnabled = false;
    private Gyroscope gyro;

    [SerializeField]
    private float SlerpFilterFactor, speed;

    private Quaternion startRotation;
    private Quaternion rotation;

    private bool fixedUpd = false;
    private bool slerp = false;
    private bool debug = true;

    #endregion
    
    void Start ()
    {
        if (SystemInfo.supportsGyroscope)
        {
            gyro = Input.gyro;
            gyro.enabled = true;
            ResetStartRotation();
            Debug.Log(gyro.attitude);
        }
    }

    private void Update()
    {
        if (!rotateEnabled || !gyro.enabled)
            return;

        if (!fixedUpd)
        {
            rotation = ConvertRotation(Quaternion.Inverse(startRotation) * gyro.attitude);
            
            if (slerp)
            {
                transform.rotation = Quaternion.Slerp(transform.rotation, rotation,
                    SlerpFilterFactor * Time.deltaTime);
            }
            else
            {
                transform.rotation = rotation;
            }
        }
    }
    
    private Quaternion ConvertRotation(Quaternion q)
    {
        return new Quaternion(q.x, q.z, q.y, -q.w);
    }

    private void RotateEnable()
    {
        rotateEnabled = !rotateEnabled;
    }

    private void SlerpEnable()
    {
        slerp = !slerp;
    }

    private void ResetStartRotation()
    {
        startRotation = gyro.attitude;
        transform.rotation = Quaternion.Euler(0.0f, 0.0f, 0.0f);
    }

    private void OnGUI()
    {
        if (!debug)
            return;


        GUILayout.BeginArea(new Rect(Screen.width - 150.0f, 0.0f, 150.0f, 300.0f));
        {
            if (GUILayout.Button("rotate control: " + rotateEnabled))
            {
                RotateEnable();
            }
            if (GUILayout.Button("reset start rotation"))
            {
                ResetStartRotation();
            }
            if (GUILayout.Button("slerp mode: " + slerp))
            {
                SlerpEnable();
            }
            GUILayout.Label("gyro attitude:" + ConvertRotation(gyro.attitude).ToString());
            GUILayout.Label("ref attitude:" + ConvertRotation(Quaternion.Inverse(startRotation) * gyro.attitude).ToString());
            GUILayout.Label("gyro rotation rate:" + ConvertRotation(Quaternion.Euler(gyro.rotationRate * speed)).ToString());
            GUILayout.Label("rotation:" + transform.rotation.ToString());
        }
        GUILayout.EndArea();

    }

}
