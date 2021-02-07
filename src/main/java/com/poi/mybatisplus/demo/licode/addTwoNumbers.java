package com.poi.mybatisplus.demo.licode;

/**
 * @Description:
 * @Author: songkai
 * @Date: 2021/1/6
 * @Version: 1.0
 */
public class addTwoNumbers {

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        ListNode head = null;
        int carry = 0;
        while (l1 != null ||  l2 != null) {
            int n1 = l1 == null ? 0 : l1.val;
            int n2 = l2 == null ? 0 : l2.val;
            int num = n1 + n2 + carry;
            if (head == null) {
                head =  new ListNode(num % 10);
            } else {
                head.next = new ListNode(num % 10);
                head = head.next;
            }
            carry = num / 10;

            if (l1 != null) {
                l1 = l1.next;
            }

            if (l2 != null) {
                l2 = l2.next;
            }
        }

        if (carry > 0){
            head.next = new ListNode(carry);
        }

        return head;
    }

    public static void main(String[] args) {

    }
}
