package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {
    private final MemberService memberService;
    // new 쓰면 여러 다른 컨트롤러들이 멤버서비스를 가져다 쓸 수 있어서 문제 생길 수 있다.

    //생성자 주입
    //가장 안전하고 많이 쓰는 방법이다.
    @Autowired
    public MemberController(MemberService memberService){
        this.memberService=memberService;
        System.out.println("memberService = "+memberService.getClass());
    }

    // 필드 주입 - 변경이 불가능하다.
    // @Autowired private MemberService memberService

    // Setter 주입 - public 하게 노출이 되는 문제가 있다. 아무개발자나 다 건들 수 있다.
    /*@Autowired
    public void setMemberService(MemberService memberService){
        this.memberService=memberService;
    }*/

    @GetMapping("/members/new")
    public String createForm(){
        return "members/createMemberForm";
    }
    @PostMapping("/members/new")
    public String create(MemberForm form){
        Member member = new Member();
        member.setName(form.getName());
        memberService.join(member);
        return "redirect:/";
    }
    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
