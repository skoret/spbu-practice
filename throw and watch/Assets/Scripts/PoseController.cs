// Copyright (c) 2018 by Sergey Skaredov
// Licensed under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

using Kalman;
using UnityEngine;

public class PoseController : MonoBehaviour {

    #region [Private fields]

    private bool poseEnabled = false;
    private bool kFilter = false;
    private Gyroscope gyro;

    [SerializeField]
    private float speed, q, r;

    private KalmanFilter kf;

    private bool debug = true;

    #endregion

    void Start ()
    {
        if (SystemInfo.supportsGyroscope)
        {
            gyro = Input.gyro;
            gyro.enabled = true;
            ResetPosition();
        }
    }

    void Update ()
    {
        Debug.Log(Time.deltaTime.ToString() + " | " + gyro.userAcceleration);

        if (!poseEnabled)
            return;
        
        if (kFilter)
        {
            kf.PoseEstimate(gyro.userAcceleration, Time.deltaTime);

            transform.position = kf.GetPose() * speed;
        }
    }

    private Vector3 ConvertPosition(Vector3 pos)
    {
        return new Vector3(pos.x, pos.z, pos.y);
    }

    private void kFilterEnable()
    {
        kFilter = !kFilter;
    }
    
    private void PoseEnable()
    {
        poseEnabled = !poseEnabled;
    }

    private void ResetPosition()
    {
        transform.position = new Vector3(0, 10, 0);

        kf = new KalmanFilter(q, r);
    }

    private void OnGUI()
    {
        if (!debug)
            return;

        GUILayout.BeginVertical();
        {
            if (GUILayout.Button("pose control: " + poseEnabled))
            {
                PoseEnable();
            }
            if (GUILayout.Button("reset position"))
            {
                ResetPosition();
            }
            if (GUILayout.Button("kFilter: " + kFilter))
            {
                kFilterEnable();
            }

            GUILayout.Label("user accel: " + gyro.userAcceleration.ToString());

            if (kFilter)
            {
                GUILayout.Label("kf acc: " + kf.GetAcc());
                GUILayout.Label("kf vel: " + kf.GetVel());
                GUILayout.Label("kf pose: " + kf.GetPose());
            }
        }
        GUILayout.EndVertical();
    }
}
