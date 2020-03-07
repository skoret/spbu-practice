using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CameraController : MonoBehaviour {

    [SerializeField]
    private GameObject target;

    public float speed;

	// Use this for initialization
	void Start () {

    }

    // Update is called once per frame
    void Update () {

        Vector3 relativePosition = target.transform.position - transform.position;

        Quaternion rotation = Quaternion.LookRotation(relativePosition, Vector3.up);
        transform.rotation = Quaternion.Slerp(transform.rotation, rotation, Time.deltaTime);
        transform.Translate(new Vector3(speed * Time.deltaTime, 0.0f, 0.0f));

    }
}
