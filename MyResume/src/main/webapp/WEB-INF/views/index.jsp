<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

  <head>
	<!-- Global site tag (gtag.js) - Google Analytics -->
		<script async src="https://www.googletagmanager.com/gtag/js?id=UA-123510889-1"></script>
		<script>
		  window.dataLayer = window.dataLayer || [];
		  function gtag(){dataLayer.push(arguments);}
		  gtag('js', new Date());
		  gtag('config', 'UA-123510889-1');
		</script>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="" >

    <title>Resume - Yoobin BAEK</title>

    <!-- Bootstrap core CSS -->
    <link href="<c:url value="/resources/vendor/bootstrap/css/bootstrap.min.css" />" rel="stylesheet">

    <!-- Custom fonts for this template -->
    <link href="https://fonts.googleapis.com/css?family=Saira+Extra+Condensed:100,200,300,400,500,600,700,800,900" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i,800,800i" rel="stylesheet">
    <link href="<c:url value="/resources/vendor/font-awesome/css/font-awesome.min.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/vendor/devicons/css/devicons.min.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/vendor/simple-line-icons/css/simple-line-icons.css"/>" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="<c:url value="/resources/css/resume.min.css"/>" rel="stylesheet">

  </head>

  <body id="page-top">

    <nav class="navbar navbar-expand-lg navbar-dark bg-primary fixed-top" id="sideNav">
      <a class="navbar-brand js-scroll-trigger" href="#page-top">
        <span class="d-block d-lg-none">Resume</span>
        <span class="d-none d-lg-block">
          <img class="img-fluid img-profile rounded-circle mx-auto mb-2" src="<c:url value="/resources/img/profile.jpg"/>" alt="">
        </span>
      </a>
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav">
          <li class="nav-item">
            <a class="nav-link js-scroll-trigger" href="#about">About</a>
          </li>
          <li class="nav-item">
            <a class="nav-link js-scroll-trigger" href="#experience">Experience</a>
          </li>
          <li class="nav-item">
            <a class="nav-link js-scroll-trigger" href="#education">Education</a>
          </li>
          <li class="nav-item">
            <a class="nav-link js-scroll-trigger" href="#skills">Skills &amp; Certifications</a>
          </li>
          <li class="nav-item">
            <a class="nav-link js-scroll-trigger" href="#interests">Interests</a>
          </li>
          <li class="nav-item">
            <a class="nav-link js-scroll-trigger" href="#awards">Portfolio</a>
          </li>
        </ul>
      </div>
    </nav>

    <div class="container-fluid p-0">

      <section class="resume-section p-3 p-lg-5 d-flex d-column" id="about">
        <div class="my-auto">
          <h1 class="mb-0">Yoobin 
            <span class="text-primary">BAEK</span>
          </h1>
          <div class="subheading mb-5">1989.06.16 · Oksudong SungDongGu, SEOUL · 010-4708-4293 ·
            <a href="mailto:youbin06@naver.com">youbin06@naver.com</a>
          </div>
          <p class="mb-5" style="font-size: 20px;">I am currently interested in web development using JSP. I have prepared my resume using HTML, CSS and JavaScript in this page. <br> You can also check out various project that I have developed. Thank you. <br>This website is optimized for the Chrome browser.</p>
          <p class="mb-5" style="font-family: 나눔 고딕; font-size: 20px;">안녕하세요. 저는 현재 JSP를 이용한 웹개발 분야에 관심이 있습니다. <br>이 페이지는 HTML, CSS, JavaScript 를 이용하여 이력서를 작성 하였으며, 제가 개발한 여러가지 프로젝트를 확인할 수 있습니다. 감사합니다. <br>현재 웹사이트는 Chrome 브라우저에 최적화 되어 있습니다.</p>
          <ul class="list-inline list-social-icons mb-0">
            <li class="list-inline-item">
              <a href="https://www.facebook.com/Yoobin.B" target="_blank">
                <span class="fa-stack fa-lg">
                  <i class="fa fa-circle fa-stack-2x"></i>
                  <i class="fa fa-facebook fa-stack-1x fa-inverse"></i>
                </span>
              </a>
            </li>
            <li class="list-inline-item">
              <a href="https://www.linkedin.com/in/%EC%9C%A0%EB%B9%88-%EB%B0%B1-ab0ab312a/" target="_blank">
                <span class="fa-stack fa-lg">
                  <i class="fa fa-circle fa-stack-2x"></i>
                  <i class="fa fa-linkedin fa-stack-1x fa-inverse"></i>
                </span>
              </a>
            </li>
            <li class="list-inline-item">
              <a href="https://github.com/youbin06" target="_blank">
                <span class="fa-stack fa-lg">
                  <i class="fa fa-circle fa-stack-2x"></i>
                  <i class="fa fa-github fa-stack-1x fa-inverse"></i>
                </span>
              </a>
            </li>
          </ul>
        </div>
      </section>

      <section class="resume-section p-3 p-lg-5 d-flex flex-column" id="experience">
        <div class="my-auto">
          <h2 class="mb-5">Experience</h2>  

          <div class="resume-item d-flex flex-column flex-md-row mb-5">
            <div class="resume-content mr-auto">
              <h3 class="mb-0">Junior Hardware Developer</h3>
              <div class="subheading mb-3">Ubiquoss R&D</div>
              <p>I worked as a hardware developer during this period. I designed PCB of various network equipments. Main tasks consisted of circuit design, CPLD code creation using VHDL, PCB assembly process, and debugging after assembling. I was able to understand the basic network equipment circuit design, structure and operation of network equipment. Also, understand the structure of the hardware language through the CPLD Code creation work, and learn how to use Oscilloscope through Debugging. Mainly designed network equipment designed terminal equipment using basic Ethernet L2 Switch and G.hn technology.</p>
            </div>
            <div class="resume-date text-md-right">
              <span class="text-primary">February 2015 - June 2017</span>
            </div>
          </div>

          <div class="resume-item d-flex flex-column flex-md-row mb-5">
            <div class="resume-content mr-auto">
              <h3 class="mb-0" style="font-family: 나눔 고딕;">하드웨어 개발 연구원</h3>
              <div class="subheading mb-3">Ubiquoss R&D</div>
              <p style="font-family: 나눔 고딕;">저는 하드웨어 개발자로 2년반 동안 근무하였습니다. 해당 기간 동안, 여러 네트워크 장비의 PCB를 설계 작업 하였고, 주된 업무는 회로 설계와 VHDL 를 이용한 CPLD Code 작성 및 PCB 조립 과정 진행과 조립 후 Debugging 작업을 진행 하였습니다. 해당 업무를 수행 하면서, 기본적인 네트워크 장비의 회로설계, 네트워크 장비의 구조와 동작 방식을 이해할 수 있었습니다. CPLD Code 작성 작업을 통해 하드웨어 언어 구조를 이해할 수 있었고, Debugging 작업을 통해 Oscilloscope 사용 방법을 터득할 수 있었습니다. 주로 설계한 네트워크 장비는 기본적인 Ethernet L2 Switch 와 G.hn 기술을 이용한 단말기 장비를 설계 하였습니다.</p>
            </div>
            <div class="resume-date text-md-right">
              <span class="text-primary">2015.02 - 2017.06</span>
            </div>
          </div>

        </div>

      </section>

      <section class="resume-section p-3 p-lg-5 d-flex flex-column" id="education">
        <div class="my-auto">
          <h2 class="mb-5">Education</h2>

          <div class="resume-item d-flex flex-column flex-md-row mb-5">
            <div class="resume-content mr-auto">
              <h3 class="mb-0">University of Handong</h3>
              <div class="subheading mb-3">Bachelor of Engineering</div>
              <div>Double Major Electronic Engineering & Computer Science </div>
              <p>GPA: 3.43</p>
            </div>
            <div class="resume-date text-md-right">
              <span class="text-primary">March 2009 - Feburary 2015</span>
            </div>
          </div>

          <div class="resume-item d-flex flex-column flex-md-row">
            <div class="resume-content mr-auto">
              <h3 class="mb-0" style="font-family: 나눔 고딕;">한동대학교</h3>
              <div class="subheading mb-3" style="font-family: 나눔 고딕;">한동대학교 공대 학사졸업</div>
              <div style="font-family: 나눔 고딕;">전자공학, 컴퓨터 공학 복수 전공 </div>
              <p>GPA: 3.43</p>
            </div>
            <div class="resume-date text-md-right">
              <span class="text-primary">2009.03 - 2015.02</span>
            </div>
          </div>

        </div>
      </section>

      <section class="resume-section p-3 p-lg-5 d-flex flex-column" id="skills">
        <div class="my-auto">
          <h2 class="mb-5">Skills &amp; Certifications</h2>

          <div class="subheading mb-3">Programming Languages &amp; Tools</div>
          <ul class="list-inline list-icons">
            <li class="list-inline-item">
              <i class="devicons devicons-java"></i>
            </li>
            <li class="list-inline-item">
              <i class="devicons devicons-mysql"></i>
            </li>
            <li class="list-inline-item">
              <i class="devicons devicons-html5"></i>
            </li>
            <li class="list-inline-item">
              <i class="devicons devicons-css3"></i>
            </li>
            <li class="list-inline-item">
              <i class="devicons devicons-javascript"></i>
            </li>
            <li class="list-inline-item">
              <i class="devicons devicons-github_badge"></i>
            </li>
            <li class="list-inline-item">
              <i class="devicons devicons-chrome"></i>
            </li>
            <li class="list-inline-item">
              <i class="devicons devicons-sublime"></i>
            </li>
            <li class="list-inline-item">
              <i class="devicons devicons-visualstudio"></i>
            </li>
            <li class="list-inline-item">
              <i class="devicons devicons-python"></i>
            </li>
          </ul>

          <div class="subheading mb-3">Certifications</div>
          <ul class="fa-ul mb-0" style="font-family: 나눔 고딕;">
            <li>
              <i class="fa-li fa fa-check"></i>
              워드프로세서 1급</li>
            <li>
              <i class="fa-li fa fa-check"></i>
              컴퓨터활용능력 3급</li>
            <li>
              <i class="fa-li fa fa-check"></i>
              운전면허1종보통</li>
          </ul>
        </div>
      </section>

      <section class="resume-section p-3 p-lg-5 d-flex flex-column" id="interests">
        <div class="my-auto">
          <h2 class="mb-5">Interests</h2>
          <p style="font-size: 20px;">I am interested in back-end development in the area of ​​Web development. All programs are working with the MVC2 pattern and working with the Spring Framework. I have recently been interested in front-end development, and I am interested in Javascript, Jquery, and CSS. If you have a chance, I would like to participate in the design side by learning the After effect and tools such as Photoshop and Illustration. Since all web pages are produced in a responsive web in recent years, I think that the publishing work is important, so I am focusing on the View Page part of all work.</p>
          <p style="font-family: 나눔 고딕; font-size: 20px;">저는 웹개발 영역에서 Back-end 개발쪽에 흥미가 있습니다. 모든 프로그램은 MVC2 패턴으로 작업하고 있으며 Spring Framework을 이용하여 작업을 하고 있습니다. 최근에는 Front-end 개발쪽에 관심이 있어, Javascript, Jquery, CSS 쪽에도 흥미가 있습니다. 기회가 된다면, After effect 와 Photoshop, Illustration 과 같은 Tool를 배워서 디자인 쪽도 직접 참여하고 싶은 생각이 있습니다. 최근 모든 웹페이지가 반응형 웹으로 제작 되어가기 때문에, Publishing 작업이 중요하다고 생각하기 때문에, 모든 작업의 View Page 부분을 가장 중점적으로 생각하고 있습니다. </p>
        </div>
      </section>

      <section class="resume-section p-3 p-lg-5 d-flex flex-column" id="awards">
        <div class="my-auto">
          <h2 class="mb-5">Awards &amp; Certifications</h2>
          <ul class="fa-ul mb-0" style="font-size: 20px;">
          	<li>
          		<a href="project1.app" target="_blank">1. Project #1(Click for the project #1)</a>
          		<ul class="fa-ul mb-0" style="font-size: 20px;">
          			<li><i class="fa-li fa fa-user text-warning"></i>Join / Login Service</li>
		            <li><i class="fa-li fa fa-user text-warning"></i>Board Service with file-upload</li>
		            <li><i class="fa-li fa fa-shield text-warning"></i>Live Chatting Service.</li>
		            <li><i class="fa-li fa fa-user text-warning"></i>Anonymous Chatting Service.</li>
          		</ul>
          	</li>	
          	<li>
          		<a href="project2.app" target="_blank">2. Project #2(Click for the project #2)</a>
          		<ul class="fa-ul mb-0" style="font-size: 20px;">
          			<li><i class="fa-li fa fa-user text-warning"></i>Join / Login Service</li>
          			<li><i class="fa-li fa fa-map text-warning"></i>Lecture Assessment System</li>
            		<li><i class="fa-li fa fa-youtube text-warning"></i>Youtube lecture Service.</li>  
          		</ul>
          	</li>
          </ul>
        </div>
      </section>

    </div>

    <!-- Bootstrap core JavaScript -->
    <script src="<c:url value="/resources/vendor/jquery/jquery.min.js"/>"></script>
    <script src="<c:url value="/resources/vendor/bootstrap/js/bootstrap.bundle.min.js"/>"></script>

    <!-- Plugin JavaScript -->
    <script src="<c:url value="/resources/vendor/jquery-easing/jquery.easing.min.js"/>"></script>

    <!-- Custom scripts for this template -->
    <script src="<c:url value="/resources/js/resume.min.js"/>"></script>
	
	
  </body>

</html>