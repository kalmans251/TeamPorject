/*
 * package com.himedia.member.controller;
 * 
 * import java.security.Principal; import java.util.List; import
 * java.util.Optional;
 * 
 * import org.springframework.stereotype.Controller; import
 * org.springframework.ui.Model; import
 * org.springframework.web.bind.annotation.GetMapping; import
 * org.springframework.web.bind.annotation.PathVariable; import
 * org.springframework.web.bind.annotation.PostMapping; import
 * org.springframework.web.bind.annotation.RequestMapping;
 * 
 * import com.himedia.member.dto.MemberAddrForm; import
 * com.himedia.member.entity.Member; import
 * com.himedia.member.entity.MemberAddress; import
 * com.himedia.member.service.MemberService;
 * 
 * import jakarta.validation.Valid; import lombok.RequiredArgsConstructor;
 * 
 * @Controller
 * 
 * @RequiredArgsConstructor
 * 
 * @RequestMapping("/member") public class MemberAddrCotroller {
 * 
 * private final MemberService memberService; //memberAddr 관련 controller메소드
 * 
 * @GetMapping("/create/addr") public String addrAddForm(MemberAddrForm
 * memberAddrForm,Model model,Principal principal) { Member member =
 * this.memberService.getMember(principal.getName()); List<MemberAddress>
 * memberaddr = this.memberService.findMemberAddr(member);
 * model.addAttribute("memberAddr", memberaddr); Optional <MemberAddress>
 * memberAddress = this.memberService.findMemberMainAddr(member);
 * model.addAttribute("memberMainAddr",memberAddress.get()); return
 * "addr_create"; }
 * 
 * @PostMapping("/create/addr") public String addrAdd(@Valid MemberAddrForm
 * memberAddrForm,Principal principal) { String addr
 * =memberAddrForm.getAddr2()+memberAddrForm.getAddr3(); Member member =
 * this.memberService.getMember(principal.getName()); int addr1 =
 * Integer.parseInt(memberAddrForm.getAddr1());
 * this.memberService.addrInsert(member,addr1,
 * addr,memberAddrForm.getReference());
 * 
 * return "redirect:/member/create/addr"; }
 * 
 * @GetMapping(value="/addr/delete/{idx}") public String addrdel(@PathVariable
 * Long idx) { this.memberService.memberAddrDel(idx);
 * 
 * return "redirect:/member/create/addr"; } //member addr change 관련 controller
 * 
 * @GetMapping(value="/addr/main/change/{idx}") public String
 * changeMainAddr(@PathVariable Long idx, Principal principal) { Member mebmer =
 * this.memberService.getMember(principal.getName());
 * this.memberService.AddrChange(mebmer);
 * this.memberService.AddrChangeMain(idx);
 * 
 * return "redirect:/member/create/addr"; } }
 */
