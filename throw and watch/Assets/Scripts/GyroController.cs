// Copyright (c) 2018 by Sergey Skaredov
// Licensed under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class GyroController : MonoBehaviour {

    //public bool isFlat = true;

    #region [Private fields]

    [SerializeField]
    private float lowPassFilterFactor = 1.5f;

    private Quaternion startRotation;

    private Gyroscope gyro;
    private bool gyroEnabled;

    private bool fixedUpd = false;
    private bool slerp = false;
    private bool debug = true;

    #endregion

    void Start ()
    {
        gyroEnabled = SystemInfo.supportsGyroscope;
        if (gyroEnabled)
        {
            gyro = Input.gyro;
            gyro.enabled = true;
            ResetStartRotation();
        }
    }

    private void Update()
    {
        if (!gyroEnabled)
            return;

        if (!fixedUpd)
        {
            Quaternion rotation = ConvertRotation(Quaternion.Inverse(startRotation) * gyro.attitude);

            if (slerp)
            {
                transform.rotation = Quaternion.Slerp(transform.rotation, rotation,
                    lowPassFilterFactor * Time.deltaTime);
            }
            else
            {
                transform.rotation = rotation;
            }
        }
    }

    void FixedUpdate()
    {
        if (!gyroEnabled)
            return;

        if (fixedUpd)
        {
            Quaternion rotation = ConvertRotation(Quaternion.Inverse(startRotation) * gyro.attitude);

            if (slerp)
            {
                transform.rotation = Quaternion.Slerp(transform.rotation, rotation,
                    lowPassFilterFactor * Time.deltaTime);
            }
            else
            {
                transform.rotation = rotation;
            }
        }
    }

    private void OnGUI()
    {
        if (!debug)
            return;

        GUI.Label(new Rect(550.0f, 10.0f, 140.0f, 20.0f), startRotation.ToString());
        GUI.Label(new Rect(550.0f, 25.0f, 140.0f, 20.0f), gyro.attitude.ToString());
        GUI.Label(new Rect(550.0f, 40.0f, 140.0f, 20.0f), (gyro.attitude * Quaternion.Inverse(startRotation)).ToString());
        GUI.Label(new Rect(550.0f, 55.0f, 140.0f, 20.0f), fixedUpd ? "FixedUpdate mode" : "Update mode");
        GUI.Label(new Rect(550.0f, 70.0f, 140.0f, 20.0f), slerp ? "Slerp is used" : "Slerp isn't used");

        if (GUILayout.Button("reset start rotation"))
        {
            ResetStartRotation();
        }
        if (GUILayout.Button("change update mode"))
        {
            ChangeUpdateMode();
        }
        if (GUILayout.Button("change slerp mode"))
        {
            ChangeSlerpMode();
        }

    }

    private Quaternion ConvertRotation(Quaternion q)
    {
        return new Quaternion(q.x, q.z, q.y, -q.w);
    }
    
    private void ResetStartRotation()
    {
        startRotation = gyro.attitude;
    }

    private void ChangeUpdateMode()
    {
        fixedUpd = !fixedUpd;
    }

    private void ChangeSlerpMode()
    {
        slerp = !slerp;
    }
}
