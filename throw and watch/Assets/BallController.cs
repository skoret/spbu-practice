// Copyright (c) 2018 by Sergey Skaredov
// Licensed under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class BallController : MonoBehaviour {

    public bool isFlat = true;
    private Rigidbody rigid;

	void Start () {
        rigid = GetComponent<Rigidbody>();
	}
	
	void Update () {

        Vector3 tilt = Input.acceleration;

        if (isFlat)
        {
            tilt = Quaternion.Euler(90, 0, 0) * tilt;
        }

        rigid.AddForce(tilt);

    }
}
