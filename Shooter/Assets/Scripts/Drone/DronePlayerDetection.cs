using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class DronePlayerDetection : MonoBehaviour
{
    public GameObject droneEnemy;

    private void OnTriggerEnter(Collider other)
    {
        droneEnemy.GetComponent<DroneMovement>().startMoving = true;
    }

    private void OnTriggerExit(Collider other)
    {
        droneEnemy.GetComponent<DroneMovement>().startMoving = false;
    }
}
