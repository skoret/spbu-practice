using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CameraController : MonoBehaviour {

    [SerializeField]
    private GameObject target;

	// Use this for initialization
	void Start () {

        transform.position = new Vector3(target.transform.position.x,
            target.transform.position.y + 4, target.transform.position.z - 14);
        transform.rotation = Quaternion.LookRotation(target.transform.position - transform.position, Vector3.up);

    }

    // Update is called once per frame
    void Update () {
		
	}
}
