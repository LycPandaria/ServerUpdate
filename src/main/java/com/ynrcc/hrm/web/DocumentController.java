package com.ynrcc.hrm.web;

import com.ynrcc.hrm.model.Document;
import com.ynrcc.hrm.model.User;
import com.ynrcc.hrm.service.HrmService;
import com.ynrcc.hrm.util.common.Constants;
import com.ynrcc.hrm.util.tag.PageModel;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;

@Controller
public class DocumentController {
    /**
     * 自动注入UserService
     * */
    @Autowired
    @Qualifier("hrmService")
    private HrmService hrmService;

    /**
     * 查询文档信息
     * @param model
     * @param pageIndex 页码
     * @param document 模糊查询参数
     * @return
     */
    @RequestMapping(value = "/document/select")
    public String selectDocument(
            Model model, Integer pageIndex,
            @ModelAttribute Document document){
        PageModel pageModel = new PageModel();
        if(pageIndex != null){
            pageModel.setPageIndex(pageIndex);
        }
        /** 查询用户信息     */
        List<Document> documents = hrmService.findDocument(document, pageModel);
        model.addAttribute("documents", documents);
        model.addAttribute("pageModel", pageModel);
        return "document/document";
    }

    /**
     *
     * @param flag 1表示跳转到上传页面，2表示执行上传操作
     * @param document 要增加的文档信息
     * @param mv
     * @param session 用于获取当前用户
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/document/add")
    public ModelAndView addDocument(String flag,
                                    @ModelAttribute Document document,
                                    ModelAndView mv, HttpSession session) throws Exception{
        if(flag.equals("1")){
            mv.setViewName("document/addDocument");
        }else{
            // 上传文件路径
            String path = session.getServletContext().getRealPath("/upload");
            // 上传文件文
            String fileName = document.getFile().getOriginalFilename();
            document.getFile().transferTo(new File(path+File.separator+ fileName));

            // 插入数据库
            // 设置 fileName
            document.setFileName(fileName);
            // 设置上传者
            User user = (User)session.getAttribute(Constants.USER_SESSION);
            document.setUser(user);

            // 插入数据库
            hrmService.addDocument(document);
            mv.setViewName("redirect:/document/select");
        }
        return mv;
    }

    /**
     * 处理删除文档请求
     * @param  ids 需要删除的id字符串
     * @param  mv
     * */
    @RequestMapping(value="/document/delete")
    public ModelAndView removeDocument(String ids,ModelAndView mv){
        // 分解id字符串
        String[] idArray = ids.split(",");
        for(String id : idArray){
            // 根据id删除文档
            hrmService.removeDocumentById(Integer.parseInt(id));
        }
        // 设置客户端跳转到查询请求
        mv.setViewName("redirect:/document/selectD");
        // 返回ModelAndView
        return mv;
    }

    /**
     * 处理修改文档请求
     * @param  flag 标记， 1表示跳转到修改页面，2表示执行修改操作
     * @param document 要修改文档的对象
     * @param mv
     * */
    @RequestMapping(value="/document/update")
    public ModelAndView updateDocument(
            String flag,
            @ModelAttribute Document document,
            ModelAndView mv){
        if(flag.equals("1")){
            // 根据id查询文档
            Document target = hrmService.findDocumentById(document.getId());
            // 设置Model数据
            mv.addObject("document", target);
            // 设置跳转到修改页面
            mv.setViewName("document/updateDocument");
        }else{
            // 执行修改操作
            hrmService.modifyDocument(document);
            // 设置客户端跳转到查询请求
            mv.setViewName("redirect:/document/select");
        }
        // 返回
        return mv;
    }

    @RequestMapping(value = "document/download")
    public ResponseEntity<byte[]> download(Integer id,
                                           HttpSession session) throws Exception{
        // 根据 ID 查询文档
        Document target = hrmService.findDocumentById(id);
        String fileName = target.getFileName();
        // 获取上传文件路径
        String path = session.getServletContext().getRealPath("/upload");
        // 获得要下载的文件
        File file = new File(path+File.separator+fileName);
        // 创建 HttpHeaders
        HttpHeaders headers = new HttpHeaders();
        // 下载显示的文件名，解决中文名称乱码问题
        String downloadFileName = new String(fileName.getBytes("UTF-8"),"iso-8859-1");
        // 通知浏览器以 attachment 打开
        headers.setContentDispositionFormData("attachment", downloadFileName);
        // application/octet-stream: 二进制流数据
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        // 201 HttpStatus.CREATED
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
    }
}
