using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;
using TMPro;

public class GUIController : MonoBehaviour {

    GameObject Phone;
    TextMeshProUGUI Rotation;
    TextMeshProUGUI Position;

    void Start()
    {
        Phone = GameObject.Find("PhoneFrame");
        Rotation = GameObject.Find("Rotation").GetComponent<TextMeshProUGUI>();
        Position = GameObject.Find("Position").GetComponent<TextMeshProUGUI>();
    }

    void Update()
    {
        Rotation.text = Phone.transform.rotation.eulerAngles.ToString();
        Position.text = Phone.transform.position.ToString();
    }

    public void PlayGame()
    {
        SceneManager.LoadScene("Game");
    }

    public void QuitGame()
    {
        SceneManager.LoadScene("Menu");
    }
}
