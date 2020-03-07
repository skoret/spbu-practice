// Copyright (c) 2018 by Sergey Skaredov
// Licensed under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

using UnityEngine;

public class PoseController : MonoBehaviour {

    #region [Private fields]

    private bool poseEnabled = true;
    private Gyroscope gyro;

    [SerializeField]
    private float speed, lpFactor, hpFactor, timeReset;

    private bool lowpass = true;
    private bool highpass = true;

    private float time = 0.0f;
    private float timeOld = 0.0f;

    #region [CalculatedVariables]

    enum Value
    {
        Acceleration,
        Velocity,
        Position
    }

    private Vector3 acceleration;
    private Vector3 velocity;
    private Vector3 position;

    #endregion
    
    private bool debug = false;

    #endregion

    void Start ()
    {
        if (SystemInfo.supportsGyroscope)
        {
            gyro = Input.gyro;
            gyro.enabled = true;
            ResetPosition();

            InvokeRepeating("ResetVars", timeReset, timeReset);

        }
    }

    void Update ()
    {
        if (!poseEnabled)
            return;
        
        float dt = Time.deltaTime;
        
        if (lowpass) acceleration = LowPassFilter(-1 * gyro.userAcceleration, Value.Acceleration);
        if (highpass) acceleration = HighPassFilter(-1 * gyro.userAcceleration, dt, Value.Acceleration);
        if (!lowpass && !highpass) acceleration = -1 * gyro.userAcceleration;

        position = HighPassFilter((velocity * dt + acceleration * dt * dt * 0.5f) * speed, dt, Value.Position);

        velocity = HighPassFilter(acceleration * dt, dt, Value.Velocity);

        transform.Translate(ConvertPosition(position));
    }

    private Vector3 LowPassFilter(Vector3 new_val, Value val)
    {
        Vector3 old_val = Vector3.zero;

        switch (val)
        {
            case Value.Acceleration:
                old_val = acceleration;
                break;
            case Value.Velocity:
                old_val = velocity;
                break;
            case Value.Position:
                old_val = position;
                break;
        }

        return lpFactor * old_val + (1 - lpFactor) * new_val;
    }

    private Vector3 HighPassFilter(Vector3 new_val, float dt, Value val)
    {
        Vector3 old_val = Vector3.zero;

        switch (val)
        {
            case Value.Acceleration:
                old_val = acceleration;
                break;
            case Value.Velocity:
                old_val = velocity;
                break;
            case Value.Position:
                old_val = position;
                break;
        }

        float factor = hpFactor / (hpFactor + dt);

        return old_val + factor * (new_val - old_val);
    }
    
    private Vector3 ConvertPosition(Vector3 pos)
    {
        return new Vector3(pos.x, pos.z, pos.y);
    }

    #region [EnableFuncs]

    private void PoseEnable()
    {
        poseEnabled = !poseEnabled;
    }

    private void lpEnable()
    {
        lowpass = !lowpass;
    }

    private void hpEnable()
    {
        highpass = !highpass;
    }

    #endregion

    #region [ResetFuncs]

    private void ResetVars()
    {
        acceleration = Vector3.zero;
        velocity = Vector3.zero;
        position = Vector3.zero;
    }

    public void ResetPosition()
    {
        ResetVars();

        transform.position = new Vector3(0, 10, 0);
    }

    #endregion

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
            GUILayout.Label("user accel: " + gyro.userAcceleration.ToString());

            GUILayout.Label("acc: " + acceleration);
            GUILayout.Label("vel: " + velocity);
            GUILayout.Label("pose: " + position);
        }
        GUILayout.EndVertical();
    }
}
