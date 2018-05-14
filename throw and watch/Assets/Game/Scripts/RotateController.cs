// Copyright (c) 2018 by Sergey Skaredov
// Licensed under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

using UnityEngine;

public class RotateController : MonoBehaviour {

    #region [Private fields]

    private bool rotateEnabled = true;
    private Gyroscope gyro;

    [SerializeField]
    private float SlerpFilterFactor;

    private Quaternion startRotation;
    private Quaternion rotation;
    
    private bool slerp = false;
    private bool debug = false;

    #endregion
    
    void Start ()
    {
        if (SystemInfo.supportsGyroscope)
        {
            gyro = Input.gyro;
            gyro.enabled = true;
            ResetRotation();
        }
    }

    void Update()
    {
        if (!rotateEnabled || !gyro.enabled)
            return;

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
    
    private Quaternion ConvertRotation(Quaternion q)
    {
        return new Quaternion(q.x, q.z, q.y, -q.w);
    }

    #region [EnableFuncs]

    private void RotateEnable()
    {
        rotateEnabled = !rotateEnabled;
    }

    private void SlerpEnable()
    {
        slerp = !slerp;
    }
    
    #endregion

    public void ResetRotation()
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
                ResetRotation();
            }
            if (GUILayout.Button("slerp mode: " + slerp))
            {
                SlerpEnable();
            }
            GUILayout.Label("rotation:" + transform.rotation.ToString());
        }
        GUILayout.EndArea();

    }

}
