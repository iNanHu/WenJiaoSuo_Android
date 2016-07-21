package com.inanhu.wenjiaosuo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.inanhu.wenjiaosuo.R;
import com.inanhu.wenjiaosuo.base.BaseActivity;
import com.inanhu.wenjiaosuo.base.GlobalValue;
import com.inanhu.wenjiaosuo.base.MessageFlag;
import com.inanhu.wenjiaosuo.util.AssetsUtil;
import com.inanhu.wenjiaosuo.util.RegexUtil;
import com.inanhu.wenjiaosuo.util.ToastUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.AddressPicker;

/**
 * 完善用户资料（第一步）
 * <p/>
 * Created by Jason on 2016/7/6.
 */
public class ProfileCompleteOneActivity extends BaseActivity {

    @BindView(R.id.et_complete_realname)
    EditText etCompleteRealname;
    @BindView(R.id.sp_complete_certificate_type)
    Spinner spCompleteCertificateType;
    @BindView(R.id.et_complete_certificate_number)
    EditText etCompleteCertificateNumber;
    @BindView(R.id.et_complete_address)
    EditText etCompleteAddress;
    @BindView(R.id.et_complete_telphone)
    EditText etCompleteTelphone;
    @BindView(R.id.et_complete_bank)
    EditText etCompleteBank;
    @BindView(R.id.et_complete_account_number)
    EditText etCompleteAccountNumber;
    @BindView(R.id.et_complete_bank_location)
    EditText etCompleteBankLocation;
    @BindView(R.id.et_complete_branch_name)
    EditText etCompleteBranchName;
    @BindView(R.id.rb_complete_man)
    RadioButton rbCompleteMan;
    @BindView(R.id.tv_complete_province)
    TextView tvCompleteProvince;

    private String certificateType;

    @OnClick(R.id.btn_complete_next)
    public void toNext() {
        String realname = etCompleteRealname.getText().toString().trim();
        String certificateNumber = etCompleteCertificateNumber.getText().toString().trim();
        String telphone = etCompleteTelphone.getText().toString().trim();
        String province = tvCompleteProvince.getText().toString().trim();
        String address = etCompleteAddress.getText().toString().trim();
        String bank = etCompleteBank.getText().toString().trim();
        String accountNumber = etCompleteAccountNumber.getText().toString().trim();
        String bankLocation = etCompleteBankLocation.getText().toString().trim();
        String branchName = etCompleteBranchName.getText().toString().trim();
        if (TextUtils.isEmpty(realname)) {
            ToastUtil.showToast("请填写真实姓名");
            return;
        }
        if (TextUtils.isEmpty(certificateNumber)) {
            ToastUtil.showToast("请填写证件号码");
            return;
        }
        if (!RegexUtil.checkMobile(telphone)) {
            ToastUtil.showToast("手机号输入有误");
            return;
        }
        if (TextUtils.isEmpty(province)) {
            ToastUtil.showToast("请选择省市县");
            return;
        }
        if (TextUtils.isEmpty(address)) {
            ToastUtil.showToast("请填写街道地址");
            return;
        }
        if (TextUtils.isEmpty(bank)) {
            ToastUtil.showToast("请填写出金银行");
            return;
        }
        if (TextUtils.isEmpty(accountNumber)) {
            ToastUtil.showToast("请填写银行账号");
            return;
        }
        if (TextUtils.isEmpty(bankLocation)) {
            ToastUtil.showToast("请填写银行所在地");
            return;
        }
        if (TextUtils.isEmpty(branchName)) {
            ToastUtil.showToast("请填写支行名称");
            return;
        }
        // 保存用户信息
        GlobalValue.getInstance().saveGlobal(MessageFlag.REALNAME, realname);
        GlobalValue.getInstance().saveGlobal(MessageFlag.GENDER, rbCompleteMan.isChecked() ? 1 : 2);
        GlobalValue.getInstance().saveGlobal(MessageFlag.CERTIFICATE_TYPE, certificateType);
        GlobalValue.getInstance().saveGlobal(MessageFlag.CERTIFICATE_NUMBER, certificateNumber);
        GlobalValue.getInstance().saveGlobal(MessageFlag.TELPHONE, telphone);
        GlobalValue.getInstance().saveGlobal(MessageFlag.ADDRESS, province + address);
        GlobalValue.getInstance().saveGlobal(MessageFlag.BANK, bank);
        GlobalValue.getInstance().saveGlobal(MessageFlag.ACCOUNT_NUMBER, accountNumber);
        GlobalValue.getInstance().saveGlobal(MessageFlag.BANK_LOCATION, bankLocation);
        GlobalValue.getInstance().saveGlobal(MessageFlag.BRANCH_NAME, branchName);

        startActivity(new Intent(ProfileCompleteOneActivity.this, ProfileCompleteTwoActivity.class));


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_complete_one);
        ButterKnife.bind(this);
        showTopBarBack(true);
        showTopBarRight(false);
        setTopBarTitle(R.string.profile_complete);
        initListener();
    }

    private void initListener() {
        spCompleteCertificateType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] certifacateTypes = getResources().getStringArray(R.array.certificate_type);
                certificateType = certifacateTypes[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @OnClick(R.id.btn_complete_select_province)
    public void onClick() {
        ArrayList<AddressPicker.Province> data = new ArrayList<AddressPicker.Province>();
        String json = AssetsUtil.readText(this, "city.json");
        data.addAll(JSON.parseArray(json, AddressPicker.Province.class));
        AddressPicker picker = new AddressPicker(this, data);
        picker.setSelectedItem("上海市", "上海市", "徐汇区");
        //picker.setHideProvince(true);//加上此句举将只显示地级及县级
        //picker.setHideCounty(true);//加上此句举将只显示省级及地级
        picker.setOnAddressPickListener(new AddressPicker.OnAddressPickListener() {
            @Override
            public void onAddressPicked(String province, String city, String county) {
                tvCompleteProvince.setText(province + city + county);
            }
        });
        picker.show();
    }
}
