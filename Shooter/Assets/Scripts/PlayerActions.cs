using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PlayerActions : MonoBehaviour
{
    public Transform playerRespawn;

    private void OnTriggerEnter(Collider other)
    {
        if (other.gameObject.CompareTag("ammo"))
        {
            Destroy(other.gameObject);
            GameManager.Instance.ammo += other.gameObject.GetComponent<Lootbox>().ammo;

            GameManager.Instance.textAmmo.text = GameManager.Instance.ammo.ToString();
        }
        if (other.gameObject.CompareTag("health"))
        {
            Destroy(other.gameObject);
            GameManager.Instance.health += other.gameObject.GetComponent<HealthBox>().health;

            GameManager.Instance.textHealth.text = GameManager.Instance.health.ToString();
        }
        if (other.gameObject.CompareTag("limitFloor"))
        {
            GetComponent<CharacterController>().enabled = false;
            this.gameObject.transform.position = playerRespawn.position;
            GetComponent<CharacterController>().enabled = true;
            GameManager.Instance.health -= 30;
            GameManager.Instance.textHealth.text = GameManager.Instance.health.ToString();
            GameManager.Instance.checkHealth();
        }
    }

}
